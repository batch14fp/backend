package com.lawencon.community.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.constant.RoleEnum;
import com.lawencon.community.dao.BankPaymentDao;
import com.lawencon.community.dao.FileDao;
import com.lawencon.community.dao.PaymentDao;
import com.lawencon.community.dao.SalesSettingDao;
import com.lawencon.community.dao.SubscriptionDao;
import com.lawencon.community.dao.UserDao;
import com.lawencon.community.dao.VoucherDao;
import com.lawencon.community.dao.WalletDao;
import com.lawencon.community.model.BankPayment;
import com.lawencon.community.model.File;
import com.lawencon.community.model.Payment;
import com.lawencon.community.model.SalesSettings;
import com.lawencon.community.model.Subscription;
import com.lawencon.community.model.User;
import com.lawencon.community.model.Voucher;
import com.lawencon.community.model.Wallet;
import com.lawencon.community.pojo.PojoRes;
import com.lawencon.community.pojo.payment.PojoConfirmPaymentReqUpdate;
import com.lawencon.community.pojo.payment.PojoPaymentDetailRes;
import com.lawencon.community.pojo.payment.PojoPaymentDetailResData;
import com.lawencon.community.pojo.payment.PojoUserPaymentReqUpdate;
import com.lawencon.community.util.GenerateString;
import com.lawencon.security.principal.PrincipalService;

@Service
public class PaymentService {

	@Autowired
	private PrincipalService principalService;

	private PaymentDao paymentDao;
	private BankPaymentDao bankPaymentDao;
	private FileDao fileDao;
	private WalletDao walletDao;
	private SalesSettingDao salesSettingDao;
	private UserDao userDao;
	private SubscriptionDao subscriptionDao;
	private VoucherDao voucherDao;

	public PaymentService(final VoucherDao voucherDao, final SubscriptionDao subscriptionDao, final UserDao userDao,
			final SalesSettingDao salesSettingDao, final WalletDao walletDao, final FileDao fileDao,
			final BankPaymentDao bankPaymentDao, final PaymentDao paymentDao) {
		this.paymentDao = paymentDao;
		this.bankPaymentDao = bankPaymentDao;
		this.fileDao = fileDao;
		this.walletDao = walletDao;
		this.salesSettingDao = salesSettingDao;
		this.voucherDao = voucherDao;
		this.userDao = userDao;
		this.subscriptionDao = subscriptionDao;

	}


	private void validateNonBk(PojoUserPaymentReqUpdate payment) {

		if (payment.getPaymentId() == null) {
			throw new RuntimeException("Payment cannot be empty.");
		}

		if (payment.getVer() == null) {
			throw new RuntimeException("Payment Version cannot be empty.");
		}
		
		if(payment.getBankPaymentId()==null) {
			throw new RuntimeException("Bank Payment cannot be empty.");
		}
		if(payment.getFileContent()==null) {
			throw new RuntimeException("File content cannot be empty.");
		}
		if(payment.getFileExtension()==null) {
			throw new RuntimeException("File extension cannot be empty.");
		}
	}
	private void validateNonBk(PojoConfirmPaymentReqUpdate payment) {

		if (payment.getPaymentId() == null) {
			throw new RuntimeException("Payment ID cannot be empty.");
		}

		if (payment.getVer() == null) {
			throw new RuntimeException("Payment version cannot be empty.");
		}
		if (payment.getIsPaid() == null) {
			throw new RuntimeException("Payment paid status cannot be empty.");
		}

	}
	


	public PojoRes updateByAdmin(PojoConfirmPaymentReqUpdate data) {
		validateNonBk(data);
		ConnHandler.begin();
		final PojoRes res = new PojoRes();
		final String codeRole = userDao.getByIdRef(principalService.getAuthPrincipal()).getRole().getRoleCode();
		if (codeRole.equalsIgnoreCase(RoleEnum.ADMIN.getRoleCode())) {
			final Payment payment = paymentDao.getByIdRef(data.getPaymentId());
			paymentDao.getByIdAndDetach(payment.getId());
			payment.setVersion(data.getVer());
			payment.setIsPaid(data.getIsPaid());
			if (data.getIsPaid()) {
				if (payment.getInvoice().getActivity() == null) {
					final Wallet walletSystem = walletDao.getByUserId(principalService.getAuthPrincipal()).get();
					final Wallet walletRefSystem = walletDao.getByIdRef(walletSystem.getId());
					walletDao.getByIdAndDetach(Wallet.class, walletRefSystem.getId());
					final BigDecimal newIncomSystem = payment.getTotal();
					walletRefSystem
							.setBalance(walletRefSystem.getBalance().add(newIncomSystem).add(payment.getTaxAmount()));
					walletRefSystem.setVersion(walletRefSystem.getVersion());
					walletDao.save(walletRefSystem);
					final Subscription subs = subscriptionDao
							.getByProfileId(payment.getInvoice().getUser().getProfile().getId()).get();
					final Subscription subsRef = subscriptionDao.getByIdRef(subs.getId());
					subs.setMemberStatus(payment.getInvoice().getMemberStatus());
					subs.setProfile(payment.getInvoice().getUser().getProfile());
					subs.setStartDate(LocalDateTime.now());
					Long day = (long) payment.getInvoice().getMemberStatus().getPeriodDay();
					subs.setEndDate(LocalDateTime.now().plusDays(day));
					subscriptionDao.save(subsRef);

					res.setMessage("Update Membership Success");

				} else {
					if (payment.getInvoice().getVoucher() != null) {
						Voucher voucher = voucherDao.getByIdRef(Voucher.class,
								payment.getInvoice().getVoucher().getId());
						voucher.setUsedCount((voucher.getUsedCount() + 1));
						voucherDao.save(voucher);
					}
					final SalesSettings setting = salesSettingDao.getSalesSetting();

					final Wallet wallet = walletDao.getByUserId(payment.getInvoice().getActivity().getUser().getId())
							.get();
					final Wallet walletRef = walletDao.getByIdRef(wallet.getId());
					walletDao.getByIdAndDetach(Wallet.class, walletRef.getId());
					final BigDecimal newIncome = payment.getSubtotal()
							.multiply(BigDecimal.valueOf(setting.getMemberIncome()));
					walletRef.setBalance(walletRef.getBalance().add(newIncome));
					walletRef.setVersion(walletRef.getVersion());
					walletDao.save(walletRef);

					final Wallet walletSystem = walletDao.getByUserId(principalService.getAuthPrincipal()).get();
					final Wallet walletRefSystem = walletDao.getByIdRef(walletSystem.getId());
					walletDao.getByIdAndDetach(Wallet.class, walletRefSystem.getId());
					final BigDecimal newIncomSystem = payment.getSubtotal()
							.multiply(BigDecimal.valueOf(setting.getSystemIncome()));

					walletRefSystem
							.setBalance(walletRefSystem.getBalance().add(newIncomSystem).add(payment.getTaxAmount()));
					walletRefSystem.setVersion(walletRefSystem.getVersion());
					walletDao.save(walletRefSystem);
					res.setMessage("Sava Success");
				}

			}
			paymentDao.saveAndFlush(payment);
			ConnHandler.commit();

		} else {
			res.setMessage("Transaction cannot be performed because you are not an admin.");
		}
		return res;
	}
	public PojoRes updateByUser(PojoUserPaymentReqUpdate data) {
		validateNonBk(data);
		ConnHandler.begin();
		final Payment payment = paymentDao.getByIdRef(data.getPaymentId());
		paymentDao.getByIdAndDetach(payment.getId());
		payment.setVersion(data.getVer());

		final BankPayment bankPayment = bankPaymentDao.getByIdRef(data.getBankPaymentId());
		payment.setBankPayment(bankPayment);

		final File file = new File();
		file.setFileExtension(data.getFileExtension());
		file.setFileName(GenerateString.generateFileName(data.getFileExtension()));
		file.setFileContent(data.getFileContent());
		final File fileNew = fileDao.save(file);

		payment.setFile(fileNew);

		paymentDao.saveAndFlush(payment);
		ConnHandler.commit();
		final PojoRes res = new PojoRes();
		res.setMessage("Transaction proof uploaded successfully!");
		return res;

	}

	public PojoPaymentDetailResData getPaymentDetail(String invoiceId) {
		PojoPaymentDetailResData res = new PojoPaymentDetailResData();
		if (paymentDao.getPaymentByInvoiceId(invoiceId).isPresent()) {
			Payment data = paymentDao.getPaymentByInvoiceId(invoiceId).get();
			if (data.getDiscAmount() != null) {
				res.setDiscAmmount(data.getDiscAmount());
			}
			if (data.getFile() != null) {
				res.setFilePaymentId(data.getFile().getId());
			}
			if (data.getBankPayment() != null) {
				res.setAccountNumber(data.getBankPayment().getAccountNumber());
				res.setBankName(data.getBankPayment().getBankName());
				res.setBankPaymetId(data.getBankPayment().getId());
				res.setAccountName(data.getBankPayment().getAccountName());

			}
			if (data.getInvoice().getActivity() != null) {
				if (data.getInvoice().getActivity().getFile() != null) {
					res.setImageActivity(data.getInvoice().getActivity().getFile().getId());
				}
				res.setEndDate(data.getInvoice().getActivity().getEndDate());
				res.setActivityId(data.getInvoice().getActivity().getId());
				res.setActivityPrice(data.getInvoice().getActivity().getPrice());
				res.setTitleActivity(data.getInvoice().getActivity().getTitle());
				res.setStartDate(data.getInvoice().getActivity().getStartDate());
			}
			if (data.getInvoice().getMemberStatus() != null) {
				res.setCodeStatus(data.getInvoice().getMemberStatus().getCodeStatus());
				res.setStatusName(data.getInvoice().getMemberStatus().getStatusName());
				res.setPeriodDay(data.getInvoice().getMemberStatus().getPeriodDay());
				res.setPriceMemberShip(data.getInvoice().getMemberStatus().getPrice());
				res.setMembershipId(data.getInvoice().getMemberStatus().getId());
			}

			res.setInvoiceCode(data.getInvoice().getInvoiceCode());
			res.setInvoiceId(data.getInvoice().getId());
			res.setPaymentId(data.getId());
			res.setPaymentExpired(data.getExpired());
			res.setPaymentId(data.getId());
			res.setSubTotal(data.getSubtotal());
			res.setIsPaid(data.getIsPaid());
			res.setVer(data.getVersion());
			res.setTaxAmmount(data.getTaxAmount());
			res.setTotal(data.getTotal());
		}
		return res;
	}
	public PojoPaymentDetailRes getByUserId(Boolean isPaid, Integer offset, Integer limit) {
		PojoPaymentDetailRes paymentDetail = new PojoPaymentDetailRes();
		List<PojoPaymentDetailResData> resList = new ArrayList<>();
		final User user = userDao.getByIdRef(User.class, principalService.getAuthPrincipal());
		paymentDao.getAllPaymentByUserId(user.getId(), isPaid, offset, limit).forEach(data -> {
			PojoPaymentDetailResData res = new PojoPaymentDetailResData();
			if (data.getDiscAmount() != null) {
				res.setDiscAmmount(data.getDiscAmount());
			}
			if (data.getFile() != null) {
				res.setFilePaymentId(data.getFile().getId());
			}
			if (data.getBankPayment() != null) {
				res.setAccountName(data.getBankPayment().getAccountName());
				res.setAccountNumber(data.getBankPayment().getAccountNumber());
				res.setBankName(data.getBankPayment().getBankName());
				res.setBankPaymetId(data.getBankPayment().getId());
			}
			if (data.getInvoice().getActivity() != null) {
				if (data.getInvoice().getActivity().getFile() != null) {
					res.setImageActivity(data.getInvoice().getActivity().getFile().getId());
				}
				res.setEndDate(data.getInvoice().getActivity().getEndDate());
				res.setActivityId(data.getInvoice().getActivity().getId());
				res.setActivityPrice(data.getInvoice().getActivity().getPrice());
				res.setTitleActivity(data.getInvoice().getActivity().getTitle());
				res.setStartDate(data.getInvoice().getActivity().getStartDate());
			}
			if (data.getInvoice().getMemberStatus() != null) {
				res.setCodeStatus(data.getInvoice().getMemberStatus().getCodeStatus());
				res.setStatusName(data.getInvoice().getMemberStatus().getStatusName());
				res.setPeriodDay(data.getInvoice().getMemberStatus().getPeriodDay());
				res.setPriceMemberShip(data.getInvoice().getMemberStatus().getPrice());
				res.setMembershipId(data.getInvoice().getMemberStatus().getId());
			}

			res.setInvoiceCode(data.getInvoice().getInvoiceCode());
			res.setInvoiceId(data.getInvoice().getId());
			res.setPaymentId(data.getId());
			res.setPaymentExpired(data.getExpired());
			res.setPaymentId(data.getId());
			res.setSubTotal(data.getSubtotal());
			res.setIsPaid(data.getIsPaid());
			res.setTaxAmmount(data.getTaxAmount());
			res.setVer(data.getVersion());
			if(data.getVersion()<1) {
				res.setStatusTrans("Unpaid");
			}
			else if(data.getVersion()==1){
				res.setStatusTrans("On-Process");
			}
			else if(data.getVersion()>1) {
				if(data.getIsPaid()) {
				res.setStatusTrans("Approved");
				}
				else {
					res.setStatusTrans("Disapproved");
				}
			}
			res.setTotal(data.getTotal());
			resList.add(res);

		});
		paymentDetail.setData(resList);
		paymentDetail.setTotal(paymentDao.getAllPaymentByUserIdCount(user.getId(), isPaid));
		return paymentDetail;

	}

	public PojoPaymentDetailRes getAll(Boolean isPaid, Integer offset, Integer limit) {
		PojoPaymentDetailRes paymentDetail = new PojoPaymentDetailRes();
		List<PojoPaymentDetailResData> resList = new ArrayList<>();

		paymentDao.getAllPayment(isPaid, offset, limit).forEach(data -> {
			PojoPaymentDetailResData res = new PojoPaymentDetailResData();

			if (data.getDiscAmount() != null) {
				res.setDiscAmmount(data.getDiscAmount());
			}
			if (data.getFile() != null) {
				res.setFilePaymentId(data.getFile().getId());
			}
			if (data.getBankPayment() != null) {
				res.setAccountName(data.getBankPayment().getAccountName());
				res.setAccountNumber(data.getBankPayment().getAccountNumber());
				res.setBankName(data.getBankPayment().getBankName());
				res.setBankPaymetId(data.getBankPayment().getId());
			}
			if (data.getInvoice().getActivity() != null) {
				res.setType(data.getInvoice().getActivity().getTypeActivity().getActivityName());
				if (data.getInvoice().getActivity().getFile() != null) {
					res.setImageActivity(data.getInvoice().getActivity().getFile().getId());
				}
				res.setEndDate(data.getInvoice().getActivity().getEndDate());
				res.setActivityId(data.getInvoice().getActivity().getId());
				res.setActivityPrice(data.getInvoice().getActivity().getPrice());
				res.setTitleActivity(data.getInvoice().getActivity().getTitle());
				res.setStartDate(data.getInvoice().getActivity().getStartDate());
				res.setCategoryName(data.getInvoice().getActivity().getCategory().getCategoryName());
			}
			if (data.getInvoice().getMemberStatus() != null) {
				res.setType("Membership");
				res.setCodeStatus(data.getInvoice().getMemberStatus().getCodeStatus());
				res.setStatusName(data.getInvoice().getMemberStatus().getStatusName());
				res.setPeriodDay(data.getInvoice().getMemberStatus().getPeriodDay());
				res.setCategoryName(data.getInvoice().getMemberStatus().getStatusName());
				res.setPriceMemberShip(data.getInvoice().getMemberStatus().getPrice());
				res.setMembershipId(data.getInvoice().getMemberStatus().getId());
			}

			if (data.getFile() != null) {
				res.setFilePaymentId(data.getFile().getId());
			}
			final User user = userDao.getById(data.getUpdatedBy()).get();
			res.setNameCreated(user.getProfile().getFullname());
			res.setInvoiceCode(data.getInvoice().getInvoiceCode());
			res.setInvoiceId(data.getInvoice().getId());
			res.setPaymentId(data.getId());
			res.setPaymentExpired(data.getExpired());
			res.setPaymentId(data.getId());
			res.setIsPaid(data.getIsPaid());
			res.setSubTotal(data.getSubtotal());
			res.setVer(data.getVersion());
			res.setTaxAmmount(data.getTaxAmount());
			res.setTotal(data.getTotal());
			resList.add(res);

		});
		paymentDetail.setData(resList);
		paymentDetail.setTotal(paymentDao.getAllPaymentCount(isPaid));
		return paymentDetail;
	}

	public PojoPaymentDetailResData getPaymentDetailById(String paymentId) {
		PojoPaymentDetailResData res = new PojoPaymentDetailResData();
		if (paymentDao.getPaymentById(paymentId).isPresent()) {
			Payment data = paymentDao.getPaymentById(paymentId).get();
			if (data.getDiscAmount() != null) {
				res.setDiscAmmount(data.getDiscAmount());
			}
			if (data.getFile() != null) {
				res.setFilePaymentId(data.getFile().getId());
			}
			if (data.getBankPayment() != null) {
				res.setAccountNumber(data.getBankPayment().getAccountNumber());
				res.setBankName(data.getBankPayment().getBankName());
				res.setBankPaymetId(data.getBankPayment().getId());
				res.setAccountName(data.getBankPayment().getAccountName());

			}
			if (data.getInvoice().getActivity() != null) {
				if (data.getInvoice().getActivity().getFile() != null) {
					res.setImageActivity(data.getInvoice().getActivity().getFile().getId());
				}
				res.setEndDate(data.getInvoice().getActivity().getEndDate());
				res.setActivityId(data.getInvoice().getActivity().getId());
				res.setActivityPrice(data.getInvoice().getActivity().getPrice());
				res.setTitleActivity(data.getInvoice().getActivity().getTitle());
				res.setStartDate(data.getInvoice().getActivity().getStartDate());
			}
			if (data.getInvoice().getMemberStatus() != null) {
				res.setCodeStatus(data.getInvoice().getMemberStatus().getCodeStatus());
				res.setStatusName(data.getInvoice().getMemberStatus().getStatusName());
				res.setPeriodDay(data.getInvoice().getMemberStatus().getPeriodDay());
				res.setPriceMemberShip(data.getInvoice().getMemberStatus().getPrice());
				res.setMembershipId(data.getInvoice().getMemberStatus().getId());
			}

			res.setInvoiceCode(data.getInvoice().getInvoiceCode());
			res.setInvoiceId(data.getInvoice().getId());
			res.setPaymentId(data.getId());
			res.setPaymentExpired(data.getExpired());
			res.setPaymentId(data.getId());
			res.setIsPaid(data.getIsPaid());
			res.setSubTotal(data.getSubtotal());
			res.setVer(data.getVersion());
			res.setTaxAmmount(data.getTaxAmount());
			res.setTotal(data.getTotal());
		}
		return res;

	}

}

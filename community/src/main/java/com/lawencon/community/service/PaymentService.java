package com.lawencon.community.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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
import com.lawencon.community.dao.WalletDao;
import com.lawencon.community.model.BankPayment;
import com.lawencon.community.model.File;
import com.lawencon.community.model.Payment;
import com.lawencon.community.model.SalesSettings;
import com.lawencon.community.model.Subscription;
import com.lawencon.community.model.Wallet;
import com.lawencon.community.pojo.PojoRes;
import com.lawencon.community.pojo.payment.PojoConfirmPaymentReqUpdate;
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

	public PaymentService(final SubscriptionDao subscriptionDao, final UserDao userDao, final SalesSettingDao salesSettingDao, final WalletDao walletDao,
			final FileDao fileDao, final BankPaymentDao bankPaymentDao, final PaymentDao paymentDao) {
		this.paymentDao = paymentDao;
		this.bankPaymentDao = bankPaymentDao;
		this.fileDao = fileDao;
		this.walletDao = walletDao;
		this.salesSettingDao = salesSettingDao;
		this.userDao = userDao;
		this.subscriptionDao = subscriptionDao;

	}




	public PojoRes updateByAdmin(PojoConfirmPaymentReqUpdate data) {
		ConnHandler.begin();
		final PojoRes res = new PojoRes();
		final String codeRole = userDao.getByIdRef(principalService.getAuthPrincipal()).getRole().getRoleCode();
		if (codeRole.equalsIgnoreCase(RoleEnum.ADMIN.getRoleCode())) {
			final Payment payment = paymentDao.getByIdRef(data.getPaymentId());
			paymentDao.getByIdAndDetach(payment.getId());
			payment.setVersion(data.getVer());
			payment.setIsPaid(data.getIsPaid());
			if (data.getIsPaid()) {
				if(payment.getInvoice().getActivity()==null) {
					final Wallet walletSystem = walletDao.getByUserId(principalService.getAuthPrincipal());
					final Wallet walletRefSystem = walletDao.getByIdRef(walletSystem.getId());
					walletDao.getByIdAndDetach(Wallet.class, walletRefSystem.getId());
					final BigDecimal newIncomSystem = payment.getTotal();
					walletRefSystem.setBalance(walletRefSystem.getBalance().add(newIncomSystem).add(payment.getTaxAmount()));
					walletRefSystem.setVersion(walletRefSystem.getVersion());
					walletDao.save(walletRefSystem);
					
					final Subscription subs = subscriptionDao.getByProfileId(payment.getInvoice().getUser().getProfile().getId()).get();
					subs.setMemberStatus(payment.getInvoice().getMemberStatus());
					subs.setProfile(payment.getInvoice().getUser().getProfile());
					subs.setStartDate(LocalDateTime.now());
					Long day = (long) payment.getInvoice().getMemberStatus().getPeriodDay();
					subs.setEndDate(LocalDateTime.now().plusDays(day));
					subscriptionDao.save(subs);
					res.setMessage("Update Membership Success");
				}
				else {

					final SalesSettings setting = salesSettingDao.getSalesSetting();

					final Wallet wallet = walletDao.getByUserId(payment.getInvoice().getActivity().getUser().getId());
					final Wallet walletRef = walletDao.getByIdRef(wallet.getId());
					walletDao.getByIdAndDetach(Wallet.class, walletRef.getId());
					final BigDecimal newIncome = payment.getSubtotal().multiply(BigDecimal.valueOf(setting.getMemberIncome()));
					walletRef.setBalance(walletRef.getBalance().add(newIncome));
					walletRef.setVersion(walletRef.getVersion());
					walletDao.save(walletRef);

					final Wallet walletSystem = walletDao.getByUserId(principalService.getAuthPrincipal());
					final Wallet walletRefSystem = walletDao.getByIdRef(walletSystem.getId());
					walletDao.getByIdAndDetach(Wallet.class, walletRefSystem.getId());
					final BigDecimal newIncomSystem = payment.getSubtotal().multiply(BigDecimal.valueOf(setting.getSystemIncome()));
					
					walletRefSystem.setBalance(walletRefSystem.getBalance().add(newIncomSystem).add(payment.getTaxAmount()));
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

}

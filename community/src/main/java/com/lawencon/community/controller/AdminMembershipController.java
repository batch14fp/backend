package com.lawencon.community.controller;

import java.util.Base64;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.community.model.File;
import com.lawencon.community.pojo.PojoInsertRes;
import com.lawencon.community.pojo.PojoRes;
import com.lawencon.community.pojo.PojoUpdateRes;
import com.lawencon.community.pojo.memberstatus.PojoMemberStatusReqInsert;
import com.lawencon.community.pojo.memberstatus.PojoMemberStatusReqUpdate;
import com.lawencon.community.pojo.memberstatus.PojoMemberStatusRes;
import com.lawencon.community.service.FileService;
import com.lawencon.community.service.MemberStatusService;

@RestController
@RequestMapping("admin/member-status")
public class AdminMembershipController {
	private MemberStatusService memberStatusService;
	private FileService fileService;
	
	public AdminMembershipController( final FileService fileService,final MemberStatusService memberStatusService) {
		this.memberStatusService = memberStatusService;
		this.fileService = fileService;
	}
	
	@GetMapping
	public ResponseEntity<List<PojoMemberStatusRes>>getAllMemberStatus(){
		List<PojoMemberStatusRes> resGet = memberStatusService.getAll();;
		return new ResponseEntity<>(resGet, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<PojoInsertRes> insertMemberStatus(@RequestBody PojoMemberStatusReqInsert data){
		PojoInsertRes resGet = memberStatusService.save(data);
		return new ResponseEntity<>(resGet, HttpStatus.CREATED);
	}
	@PutMapping
	public ResponseEntity<PojoUpdateRes> updateMemberStatus(@RequestBody PojoMemberStatusReqUpdate data){
		PojoUpdateRes resGet = memberStatusService.update(data);
		return new ResponseEntity<>(resGet, HttpStatus.CREATED);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<PojoRes> deleteMemberStatus(@PathVariable ("id")String id){
		PojoRes resDelete = memberStatusService.deleteById(id);
		return new ResponseEntity<>(resDelete, HttpStatus.OK);
	}
	@GetMapping("{id}")
    public ResponseEntity<?> getFileById(@PathVariable("id") String id) {
        final Optional<File> file = fileService.getById(id);
        final String fileName = "attachment";
        final byte[] fileBytes = Base64.getDecoder().decode(file.get().getFileContent());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName + "." + file.get().getFileExtension())
                .body(fileBytes);
    }

	
	

}

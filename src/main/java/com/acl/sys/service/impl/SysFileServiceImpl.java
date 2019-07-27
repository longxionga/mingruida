/**
 * 
 */
package com.acl.sys.service.impl;

import java.io.IOException;
import java.io.InputStream;


import com.acl.goods.dao.UserInfoDao;
import com.acl.sys.service.ISysFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.acl.component.SystemConfig;
import com.acl.pojo.SysFile;
import com.anchol.common.component.storage.Storage;
/**
 * Class Name: SysFileService<br>
 * Description: 系统文件服务
 *
 * @author lilin
 * @version 1.0
 */
@Service
@Transactional
public class SysFileServiceImpl implements ISysFileService {

  @Autowired
  protected Storage storage;
	
		
	/**
	 * 仅仅删除文件服务器上的文件
	 * @param path
	 * @return
	 */
	@Transactional (propagation = Propagation.SUPPORTS,readOnly=true)
	public boolean deleteFile(String path) {
		int le=SystemConfig.imgUrl.length();
		path=path.substring(le,path.length());
		return storage.remove(path);
	}

	/**
	 * 文件上传
	 * 
	 * @param fileName
	 * @param fileType
	 */
	public SysFile fileUpload(String fileName, String fileType, InputStream is, String... prefix) {
		SysFile sysFile = null;
		String path = storage.store(is, fileName, prefix);
		if(path == null){
			return sysFile;
		}
		sysFile = new SysFile();
		sysFile.setFileName(fileName);
		sysFile.setFileType(fileType);
		sysFile.setFilePath(path);
		sysFile.setFileSize(storage.getFileSize(sysFile.getFilePath()));
		return sysFile;
	}
	
	/**
	 * 文件上传
	 * @param file
	 * @param fileType
	 * @return
	 */
	public SysFile fileUpload(MultipartFile file, String fileType, String... prefix){
		SysFile sysFile = null;
	  String path = "";
		if (!file.isEmpty()) {
			sysFile = new SysFile();
			sysFile.setFileName(file.getOriginalFilename());
			sysFile.setFileType(fileType);
			sysFile.setFilePath(path);
			sysFile.setFileSize(file.getSize());
		}
		return sysFile;
	}
	
	/**
	 * 文件上传
	 * @param file
	 * @param fileType
	 * @return
	 */
	public SysFile fileUploadImg(MultipartFile file, String fileType,String floder, String... prefix){
		SysFile sysFile = null;
	  String path = "";
		if (!file.isEmpty()) {
			try {
				path = storage.storeImg(file.getInputStream(), file.getOriginalFilename(),floder, prefix);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
			sysFile = new SysFile();
			sysFile.setFileName(file.getOriginalFilename());
			sysFile.setFileType(fileType);
			sysFile.setFilePath(path);
			sysFile.setFileSize(file.getSize());
		}
		return sysFile;
	}
	
	/**
	 * 文件上传
	 * 
	 * @param fileName
	 * @param fileType
	 */
	public SysFile fileUploadImg(String fileName, String fileType, InputStream is,String folder, String... prefix) {
		SysFile sysFile = null;
		String path = storage.store(is, fileName, prefix);
		if(path == null){
			return sysFile;
		}
		sysFile = new SysFile();
		sysFile.setFileName(fileName);
		sysFile.setFileType(fileType);
		sysFile.setFilePath(path);
		sysFile.setFileSize(storage.getFileSize(sysFile.getFilePath()));
		return sysFile;
	}
	
}

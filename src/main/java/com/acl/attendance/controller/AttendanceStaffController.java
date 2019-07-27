package com.acl.attendance.controller;

import com.acl.attendance.service.AttendanceStaffService;
import com.acl.component.SystemConfig;
import com.acl.core.CoreBaseController;
import com.acl.platform.service.IPlatformUserAgentRechargeService;
import com.acl.pojo.SysFile;
import com.acl.sys.service.*;
import com.acl.utils.paginator.domain.Order;
import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;
import com.acl.utils.util.*;
import com.anchol.common.component.session.SessionProvider;
import com.anchol.common.component.session.support.Session;
import com.anchol.common.util.file.FileUtil;
import org.apache.commons.codec.binary.Base64;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.*;

/**
 *className:AttendanceStaffController
 *author:longxionga
 *createDate:2019年5月25日 上午9:23:32
 *department:铭锐达
 *description:
 */

@Controller
@RequestMapping("/attendance")
public class AttendanceStaffController extends CoreBaseController{
	private static final Logger LOGGER = LoggerFactory.getLogger(AttendanceStaffController.class);

	
	@Autowired
	private IPlatformUserAgentRechargeService platformUserAgentRechargeService;
	
	@Autowired
	private ISysIndexService sysIndexService;
	
	@Autowired
	private ISysWithDrawService sysWithDrawService;

	@Autowired
	private AttendanceStaffService attendanceStaffService;


	@RequiresAuthentication
	@RequiresPermissions(value = { "/attendance/staffworkattendance" })
	@RequestMapping("/staffworkattendance")
	public String staffworkattendance(){
		return "attendance/attendance_staff";
	}


	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = { "/attendance/staffworkattendance" })
	@RequestMapping("/querystaffworkattendanceinfo")
	public Object querystaffworkattendanceinfo(@Session(create = false) SessionProvider session,HttpServletRequest request,
											   @RequestParam(defaultValue = "0", required = false) Integer page,
											   @RequestParam(defaultValue = "10", required = false) Integer rows,
											   @RequestParam HashMap<String,Object> paramsMap){
		String sortString = "";
		LOGGER.info("Query start... ");
		PageBounds pageBounds = new PageBounds(page,rows,Order.formString(sortString));

		//	PageList<?> list = (PageList<?>) platformUserInfoService.queryUserAgentInfo(paramsMap, pageBounds);
		PageList<?> list = (PageList<?>) attendanceStaffService.querystaffworkattendanceinfo(paramsMap, pageBounds);
		LOGGER.info("Query stop... ");
		Object json = this.getJsonMap(list);
		return json;
	}

	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = { "/attendance/staffworkattendance" })
	@RequestMapping("/insertstaffworkattendanceinfo")
	public Object insertstaffworkattendanceinfo(@RequestParam HashMap<String,Object> paramsMap,HttpServletRequest request){
		try{
//			if (paramsMap.get("attendance_time") != null) {
//				paramsMap.put("attendance_time",paramsMap.get("attendance_time").toString().replaceAll("-",""));
//
//			}
			attendanceStaffService.insertstaffworkattendanceinfo(paramsMap);
			msg.setMsg("更新成功");
			msg.setSuccess(true);
		}catch(Exception e){
			msg.setMsg("更新失败");
			msg.setSuccess(false);
			e.printStackTrace();
		}
		return msg;
	}

	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = { "/attendance/staffworkattendance" })
	@RequestMapping("/updatestaffworkattendanceinfo")
	public Object updatestaffworkattendanceinfo(@RequestParam HashMap<String,Object> paramsMap,HttpServletRequest request){
		try{
			attendanceStaffService.updatestaffworkattendanceinfo(paramsMap);
			msg.setMsg("更新成功");
			msg.setSuccess(true);
		}catch(Exception e){
			msg.setMsg("更新失败");
			msg.setSuccess(false);
			e.printStackTrace();
		}
		return msg;
	}

	/**
	* 删除考勤明细
	* @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = { "/attendance/staffworkattendance"})
	@RequestMapping("/deletestaffworkattendancedetail")
	public Object deletestaffworkattendancedetail(@Session(create = false) SessionProvider session, HttpServletRequest request,
								 @RequestParam HashMap<String, Object> paramsMap) {
		try {
			attendanceStaffService.deletestaffworkattendancedetail(paramsMap);
			msg.setMsg("操作成功");
			msg.setSuccess(true);
		} catch (Exception e) {
			msg.setMsg("操作失败");
			msg.setSuccess(false);
			e.printStackTrace();
		}
		return msg;
	}

	@ResponseBody
	@RequiresAuthentication
	@RequestMapping(value = "/uploadfileattdetail")
	public Object uploadfileattdetail(@RequestParam("upfile") MultipartFile[] file, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();
		ApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext());
		SystemConfig systemConfig = (SystemConfig) applicationContext.getBean("systemConfig");

		String attID = (String)request.getParameter("attID");
		try {
			//获得临时文件
			String path = systemConfig.getTempPath();
			//创建临时目录
			File fileDir = new File(path);
			if(!fileDir.exists() && !fileDir.isDirectory()) {
				fileDir.mkdir();
			}

			for (int i = 0; i < file.length; i++) {
				MultipartFile multipartFiles = file[i];
				//SysFile sysFile = sysFileService.fileUpload(file[i], "image");
				String fileName = UUIDGenerator.generate() + multipartFiles.getOriginalFilename();
				String filePath = path + System.getProperty("file.separator") + fileName;
				File destTemp = new File(filePath);
				InputStream is = null;
				FileUtil.writeFile(file[i].getInputStream(), destTemp);
				is = new FileInputStream(destTemp);
				//直营员工信息导入
				attendanceStaffService.staffworkattendancedetailimport(attID,destTemp);

				//SysFile sysFilePrefix = sysFileService.fileUpload(file[i].getOriginalFilename(), "image", is, filePath);
				SysFile sysFilePrefix = new SysFile();
				sysFilePrefix.setFileName(fileName);
				sysFilePrefix.setFileType("xlsx");
				sysFilePrefix.setFilePath(path);
				sysFilePrefix.setFileSize(multipartFiles.getSize());
				String url = systemConfig.getFileServBaseUrl() + sysFilePrefix.getFilePath();
				String u = Base64.encodeBase64String(url.getBytes("UTF-8"));
				result.put("url", SystemConfig.localUrl + "/upload/getImage/" + u);
				result.put("original", sysFilePrefix.getFileName());
				result.put("size", sysFilePrefix.getFileSize());
				result.put("type", sysFilePrefix.getFileType());
				result.put("state", "SUCCESS");
				is.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("state", "上传失败");
		}
		return result;
	}


}

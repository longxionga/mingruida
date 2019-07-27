package com.acl.sys.controller;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.acl.utils.util.UUIDGenerator;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.acl.core.CoreBaseController;
import com.acl.pojo.LoginSession;
import com.acl.sys.service.ISysCompanyTXDetailedService;
import com.acl.sys.service.SysDeptInfoService;
import com.acl.sys.service.ISysWithDrawService;
import com.acl.utils.paginator.domain.Order;
import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;
import com.acl.utils.util.DateFormatUtil;
import com.acl.utils.util.FormatDateUtil;
import com.acl.utils.util.StringUtils;
import com.anchol.common.component.session.SessionProvider;
import com.anchol.common.component.session.support.Session;

/**
 * className:SysIndex author:huangs createDate:2016年9月23日 下午2:50:30 vsersion:3.0
 * department:安创乐科技 description:提现审批
 */
@Controller
@RequestMapping("/sys")
public class SysWithdrawApproveController extends CoreBaseController {

    @Autowired
    private SysDeptInfoService sysDeptInfoService;

    @Autowired
    private ISysWithDrawService sysWithDrawService;
    @Autowired
    private ISysCompanyTXDetailedService sysCompanyTXDetailedService;

    /**
     * 审批
     *
     * @return
     */
    @RequiresAuthentication
    @RequiresPermissions(value = {"/sys/withDrawApprove"})
    @RequestMapping("/withDrawApprove")
    public String withDrawApprove() {
        return "sys/sys_with_draw_approve";
    }

    @RequiresAuthentication
    @RequiresPermissions(value = {"/sys/withDrawApprove"})
    @RequestMapping("/withDepositInfo")
    public String withDepositInfo() {
        return "sys/sys_deposit_info";
    }

    /**
     * @return
     * @throws ParseException
     */
    @ResponseBody
    @RequiresAuthentication
    @RequiresPermissions(value = {"/sys/withDrawApprove"})
    @RequestMapping("/queryWithDrawInfoApprove")
    public Object queryWithDrawInfoApprove(@Session(create = false) SessionProvider session, HttpServletRequest request,
                                           @RequestParam(defaultValue = "0", required = false) Integer page,
                                           @RequestParam(defaultValue = "20", required = false) Integer rows,
                                           @RequestParam HashMap<String, Object> paramsMap) {
        // 页面默认查未被处理的单据
        if ("".equals(StringUtils.checkString(paramsMap.get("is_status")))) {
            paramsMap.put("is_status", "0,1");
        }
        // 如果查询开始时间存在 则判断结束日期是否也存在 如不存在则自动取今天为结束日期（开始日期为必须时间）
        if (!"".equals(StringUtils.checkString(paramsMap.get("begin_date")))) {
            paramsMap.put("begin_date", paramsMap.get("begin_date").toString() + " 00:00:00");
            if ("".equals(StringUtils.checkString(paramsMap.get("end_date")))) {
                paramsMap.put("end_date", DateFormatUtil.convertCurrentDate("yyyy-MM-dd 23:59:59"));
            }
        }
        if (!"".equals(StringUtils.checkString(paramsMap.get("end_date")))) {
            paramsMap.put("end_date", paramsMap.get("end_date").toString() + " 23:59:59");
        }
        PageBounds pageBounds = new PageBounds(page, rows, Order.formString("is_status,create_date"));
        PageList<?> list = (PageList<?>) sysWithDrawService.queryWithDrawInfo(paramsMap, pageBounds);
        Object json = this.getJsonMap(list);
        return json;
    }

    /**
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    @RequiresPermissions(value = {"/sys/withDrawApprove"})
    @RequestMapping("/updWithDrawApproveInfo")
    public Object updateWithDrawApproveInfo(@Session(create = false) SessionProvider session, HttpServletRequest request,
                                            @RequestParam HashMap<String, Object> paramsMap) {
        LoginSession loginSession = (LoginSession) session.getAttribute("userSession");
        paramsMap.put("operation_name", loginSession.getUser_nick_name());
        paramsMap.put("user_role", loginSession.getRole_name());
//		company_id : id,
//		is_status : status,
//		type : type
        if (3 == StringUtils.checkInt(paramsMap.get("type"))) {
            //审核打回   打回要退钱
            paramsMap.put("new_status", 3);
        } else {
            //审核通过及完成
            paramsMap.put("new_status", StringUtils.checkInt(paramsMap.get("is_status")) + 1);
        }
        try {
            msg.setMsg("操作成功");
            HashMap<String, Object> txMap = sysWithDrawService.queryWithDrawInfo(paramsMap).get(0);
            BigDecimal tx_money = StringUtils.checkBigDecimal(txMap.get("tx_money"));
            paramsMap.put("tx_money", tx_money);
            if (3 == StringUtils.checkInt(paramsMap.get("type"))) {
                HashMap<String, Object> deptparMap = new HashMap<>();
                deptparMap.put("dept_id", txMap.get("dept_id"));
                Map<String, Object> deptMap = sysDeptInfoService.queryDeptInfo(deptparMap).get(0);
                //退钱操作
                BigDecimal new_money = StringUtils.checkBigDecimal(deptMap.get("dept_money")).add(tx_money);
                paramsMap.put("new_money", new_money);
                paramsMap.put("dept_id", txMap.get("dept_id"));
                paramsMap.put("dept_money", deptMap.get("dept_money"));

                HashMap<String, Object> deptparamsMap = new HashMap<>();
                deptparamsMap.put("dept_id", paramsMap.get("dept_id"));
                deptparamsMap.put("money", paramsMap.get("new_money"));
                deptparamsMap.put("dept_money", paramsMap.get("dept_money"));
                sysWithDrawService.updateDeptMoneyInfo(deptparamsMap);


                //记录流水
                HashMap<String, Object> detailMap = new HashMap<>();
                detailMap.put("sys_id", UUIDGenerator.generate());
                detailMap.put("order_no", txMap.get("company_id"));
                detailMap.put("money", txMap.get("tx_money"));
                detailMap.put("fund_type", "07");
                detailMap.put("before_balance", deptMap.get("dept_money"));
                detailMap.put("after_balance", paramsMap.get("new_money"));
                detailMap.put("create_date", FormatDateUtil.getStringDateYYMMDDHHMMSS(new Date()));

                //添加流水
                if (2 == StringUtils.checkInt(deptMap.get("dept_type"))) {
                    detailMap.put("channel_id", paramsMap.get("dept_id"));
                }
                if (3 == StringUtils.checkInt(deptMap.get("dept_type"))) {
                    detailMap.put("settle_id", paramsMap.get("dept_id"));
                }
                if (4 == StringUtils.checkInt(deptMap.get("dept_type"))) {
                    detailMap.put("agent_id", paramsMap.get("dept_id"));
                }
                sysCompanyTXDetailedService.insertCompanyTXDetailed(detailMap);

                msg.setMsg("审核打回成功");
            }
            if (2 == StringUtils.checkInt(paramsMap.get("type"))) {
                BigDecimal detp_money = StringUtils.checkBigDecimal(paramsMap.get("ex_dept_money"));
                BigDecimal detp_fee = StringUtils.checkBigDecimal(paramsMap.get("ex_dept_fee"));
                BigDecimal after_money = detp_money.add(detp_fee);
                //记录流水
                HashMap<String, Object> detailMap = new HashMap<>();
                HashMap<String, Object> deptMap = new HashMap<>();
                detailMap.put("exchange_id", paramsMap.get("ex_dept_id"));
                detailMap.put("sys_id", UUIDGenerator.generate());
                detailMap.put("order_no", paramsMap.get("company_id"));
                detailMap.put("money", paramsMap.get("ex_dept_fee"));
                detailMap.put("fund_type", "22");
                detailMap.put("before_balance", paramsMap.get("ex_dept_money"));
                detailMap.put("after_balance", after_money.doubleValue());
                detailMap.put("create_date", FormatDateUtil.getStringDateYYMMDDHHMMSS(new Date()));

                sysCompanyTXDetailedService.insertCompanyTXDetailed(detailMap);
                deptMap.put("dept_id", paramsMap.get("ex_dept_id"));
                deptMap.put("dept_money", paramsMap.get("ex_dept_money"));
                deptMap.put("money", after_money.doubleValue());
                sysWithDrawService.updateDeptMoneyInfo(deptMap);
            }
            sysWithDrawService.updateWithDrawApproveInfo(paramsMap);
            msg.setSuccess(true);
        } catch (Exception e) {
            msg.setMsg("操作失败");
            msg.setSuccess(false);
            e.printStackTrace();
        }
        return msg;
    }


    /**
     * 查询保证金充值信息
     *
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    @RequiresPermissions(value = {"/sys/withDrawApprove"})
    @RequestMapping("/queryDepositInfo")
    public Object queryDepositInfo(@Session(create = false) SessionProvider session, ModelMap modelMap, HttpServletRequest request,
                                   @RequestParam(defaultValue = "0", required = false) Integer page,
                                   @RequestParam(defaultValue = "10", required = false) Integer rows,
                                   @RequestParam HashMap<String, Object> paramsMap) {
        PageBounds pageBounds = new PageBounds(page, rows);
        LoginSession loginSession = (LoginSession) session.getAttribute("userSession");
        //查询当天的代理商信息
        //获取登录的代理商新进行条件筛选，如果没有则为空。
        //PageList<?> list = null;
        PageList<?> list = (PageList<?>) sysWithDrawService.queryDepositInfo(paramsMap, pageBounds);
        Object json = this.getJsonMap(list);
        return json;
    }

    /**
     * 保证金统计
     *
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    @RequiresPermissions(value = {"/sys/withDrawApprove"})
    @RequestMapping("/queryDepositSum")
    public Object queryDepositSum(@Session(create = false) SessionProvider session, HttpServletRequest request,
                                  @RequestParam HashMap<String, Object> paramsMap, HttpServletResponse response) {
        LoginSession loginSession = (LoginSession) session.getAttribute("userSession");
        //paramsMap.put("dept_id", loginSession.getDept_id());
        String dept_type = loginSession.getDept_type();
        //查询统计信息
        List<HashMap<String, Object>> list = new LinkedList<HashMap<String, Object>>();
        //list = null;
        list = sysWithDrawService.queryDepositCount(paramsMap);
        return list;
    }

    /**
     * 保证金更新操作
     *
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    @RequiresPermissions(value = {"/sys/withDrawApprove"})
    @RequestMapping("/updDepositInfo")
    public Object updDepositInfo(HttpServletRequest request,
                                 @RequestParam HashMap<String, Object> paramsMap) {

        //boolean retstr = true;

        sysWithDrawService.queryDeptMoneyInfo(paramsMap);
        if (paramsMap.get("retStr").equals("1")) {
            msg.setSuccess(true);
            msg.setMsg("保证金更新成功!");
        } else {
            msg.setSuccess(false);
            msg.setMsg("更新失败,请检查相关信息!");
        }
        return msg;
    }

}
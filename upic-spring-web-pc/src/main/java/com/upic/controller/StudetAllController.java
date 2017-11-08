package com.upic.controller;

import com.upic.common.beans.utils.ChineseCharToEn;
import com.upic.common.document.excel.ExcelDocument;
import com.upic.condition.*;
import com.upic.dto.*;
import com.upic.enums.GrainCoinLogStatusEnum;
import com.upic.enums.GrainCoinLogTypeEnum;
import com.upic.enums.IntegralLogStatusEnum;
import com.upic.enums.IntegralLogTypeEnum;
import com.upic.service.*;
import com.upic.social.user.SocialUsers;
import com.upic.utils.UserUtils;

//import com.upic.utils.UserUtils;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/stu")
public class StudetAllController {
    protected static final Logger LOGGER = LoggerFactory.getLogger(StudetAllController.class);

    @Autowired
    private IntegralLogService integralLogService;

    @Autowired
    private GrainCoinLogService grainCoinLogService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private BannerService bannerService;

    @Autowired
    private PrizeService prizeService;

    @Autowired
    private UserService userService;

    /**
     * 获取当前用户的积分*
     *
     * @return
     * @throws Exception
     */
    @GetMapping("/getIntegeral")
    public Double getIntegeral() throws Exception {
        try {
            // Authentication authentication =
            // SecurityContextHolder.getContext().getAuthentication();
            // System.out.println(authentication);
            // SocialUser user = null;
            // if (authentication != null) {
            // user = (SocialUser) authentication.getPrincipal();
            // }
            // if(user==null){
            // throw new Exception("获取用户失败");
            // }
            return integralLogService.watchIntegral(UserUtils.getUser().getUserId());
        } catch (Exception e) {
            LOGGER.info("getIntegeral :" + e.getMessage());
            return null;
        }
    }

    /**
     * 获取当前用户的积分*
     *
     * @return
     * @throws Exception
     */
    @GetMapping("/getIntegeralByUserNum")
    public Double getIntegeralByUserNum(String userNum) throws Exception {
        try {
            // Authentication authentication =
            // SecurityContextHolder.getContext().getAuthentication();
            // System.out.println(authentication);
            // SocialUser user = null;
            // if (authentication != null) {
            // user = (SocialUser) authentication.getPrincipal();
            // }
            // if(user==null){
            // throw new Exception("获取用户失败");
            // }
            return integralLogService.watchIntegral(userNum);
        } catch (Exception e) {
            LOGGER.info("getIntegeral :" + e.getMessage());
            return null;
        }
    }

    /**
     * 获取当前学生素拓币*
     *
     * @return
     * @throws Exception
     */
    @GetMapping("/getGrainCoin")
    public Double getGrainCoin() throws Exception {
        try {
            // Authentication authentication =
            // SecurityContextHolder.getContext().getAuthentication();
            // System.out.println(authentication);
            // SocialUser user = null;
            // if (authentication != null) {
            // user = (SocialUser) authentication.getPrincipal();
            // }
            // if(user==null){
            // throw new Exception("获取用户失败");
            // }
            return grainCoinLogService.watchGrainCoin(UserUtils.getUser().getUserId());
        } catch (Exception e) {
            LOGGER.info("getGrainCoin:" + e.getMessage());
            return null;
        }
    }

    /**
     * 获取当前学生素拓币*
     *
     * @return
     * @throws Exception
     */
    @GetMapping("/getGrainCoinByUserNum")
    public Double getGrainCoinByUserNum(String userNum) throws Exception {
        try {
            // Authentication authentication =
            // SecurityContextHolder.getContext().getAuthentication();
            // System.out.println(authentication);
            // SocialUser user = null;
            // if (authentication != null) {
            // user = (SocialUser) authentication.getPrincipal();
            // }
            // if(user==null){
            // throw new Exception("获取用户失败");
            // }
            return grainCoinLogService.watchGrainCoin(userNum);
        } catch (Exception e) {
            LOGGER.info("getGrainCoin:" + e.getMessage());
            return null;
        }
    }

    /**
     * 获取banner图*
     *
     * @param pageable
     * @param b
     * @return
     * @throws Exception
     */
    @GetMapping("/getBanner")
    public Page<BannerInfo> getBanner(@PageableDefault(size = 10) Pageable pageable, BannerCondition b) {
        try {
            return bannerService.searchBanner(b, pageable);
        } catch (Exception e) {
            LOGGER.info("getBanner:" + e.getMessage());
            return null;
        }
    }

    /**
     * 奖品交换*
     *
     * @param prizeId
     * @return
     */
    @GetMapping("/getExchangePrize")
    public String getExchangePrize(Long prizeId) {
        try {
            PrizeInfo prizeInfo = prizeService.getPrizeById(prizeId);
            if (prizeInfo != null) {
                GrainCoinLogInfo grainCoinLogInfo = new GrainCoinLogInfo();
                grainCoinLogInfo.setEvent(UserUtils.getUser().getUserId() + "兑换" + prizeInfo.getPrizeName());
                grainCoinLogInfo.setPrizeId(prizeId);
                grainCoinLogInfo.setScore(-prizeInfo.getScore());
                grainCoinLogInfo.setType(GrainCoinLogTypeEnum.PAYMENT);
                grainCoinLogInfo.setStatus(GrainCoinLogStatusEnum.HAVEDONE);
                return "SUCCESS";
            }
        } catch (Exception e) {
            LOGGER.info("getExchangePrize:" + e.getMessage());
        }
        return "ERROR";
    }

    /**
     * 查找积分列表*
     *
     * @param i
     * @param pageable
     * @return
     * @throws Exception
     */
    @GetMapping("/getPageIntegral")
    public Page<IntegralLogInfo> getPageIntegral(IntegralLogCondition i, @PageableDefault(size = 10) Pageable pageable)
            throws Exception {
        try {
            return integralLogService.searchIntegralLog(i, pageable);
        } catch (Exception e) {
            LOGGER.info("getProjectInfo:" + e.getMessage());
            return null;
        }
    }

    /**
     * 是否已报名*（需要修改）
     *
     * @param studentNum
     * @param projectNum
     * @return
     * @throws Exception
     */
    @GetMapping("/isSignUpByIntegralLogId")
    public String isSignUpByIntegralLogId(String studentNum, String projectNum) throws Exception {
        try {
            IntegralLogIdInfo integralLogIdInfo = new IntegralLogIdInfo(studentNum, projectNum);
            IntegralLogInfo integralLogInfo = integralLogService.getByIntegralLogId(integralLogIdInfo);
            return integralLogInfo == null ? "error" : "success";
        } catch (Exception e) {
            LOGGER.info("isSignUpByIntegralLogId:" + e.getMessage());
            return null;
        }
    }

    /**
     * 积分申请提交*
     *
     * @param integralLogInfo
     * @return @RequestParam("file") MultipartFile file, MultipartHttpServletRequest
     * request
     */
    @PostMapping("/postIntegralLog")
    public IntegralLogInfo postIntegralLog(IntegralLogInfo integralLogInfo) throws Exception {
        try {
            integralLogInfo.setStatus(IntegralLogStatusEnum.PENDING_AUDIT);
            integralLogInfo.setType(IntegralLogTypeEnum.VOLUNTARY_APPLICATION);
            IntegralLogIdInfo integralLogIdInfo = new IntegralLogIdInfo();
            SocialUsers userInfo = UserUtils.getUser();
            integralLogIdInfo.setStudentNum(userInfo.getUserId());
            ChineseCharToEn cte = new ChineseCharToEn();
            if (integralLogInfo.getField1().equals("radioselect1")) {
                integralLogIdInfo.setProjectNum("VOLUNTARY_APPLICATION" + integralLogInfo.getField2());
            } else {
                integralLogIdInfo.setProjectNum("VOLUNTARY_APPLICATION" + cte.getAllFirstLetter(integralLogInfo.getProjectName()));
            }

            integralLogInfo.setClazz(userInfo.getClazz());
            integralLogInfo.setCollege(userInfo.getCollege());
            integralLogInfo.setCreatTime(new Date());
            integralLogInfo.setStudent(userInfo.getUserNum());
            integralLogInfo.setIntegralLogId(integralLogIdInfo);
            integralLogInfo.setCollegeOtherName(cte.getAllFirstLetter(userInfo.getCollege()).toUpperCase());
            integralLogInfo = integralLogService.saveIntegralLog(integralLogInfo);
            return integralLogInfo;
        } catch (Exception e) {
            LOGGER.info("postIntegralLog:" + e.getMessage());
            return null;
        }
    }

    /**
     * 根据自身编号获取参加过的自主申报项目（可能没用）
     *
     * @param pageable
     * @return
     * @throws Exception
     */
    @GetMapping("/getIntegralLogByMySelf")
    public Page<IntegralLogInfo> getIntegralLogByMySelf(@PageableDefault(size = 10) Pageable pageable) {
        try {
            return integralLogService.getIntegralLogByMySelf(UserUtils.getUser().getUserId(), pageable);
        } catch (Exception e) {
            LOGGER.info("getIntegralLogByMySelf:" + e.getMessage());
            return null;
        }
    }

    /**
     * 学生查看自身的积分日志（可能没用）
     *
     * @param pageable
     * @return
     * @throws Exception
     */
    @GetMapping("/getAllIntegralLogByStudentNum")
    public Page<IntegralLogInfo> getAllIntegralLogByStudentNum(@PageableDefault(size = 10) Pageable pageable)
            throws Exception {
        try {
            Page<IntegralLogInfo> integralLogInfoPage = integralLogService
                    .getAllIntegralLogByStudentNum(UserUtils.getUser().getUserId(), pageable);
            return integralLogInfoPage;
        } catch (Exception e) {
            LOGGER.info("getAllIntegralLogByStudentNum:" + e.getMessage());
            return null;
        }
    }

    /**
     * 学生查看积分日志（可能没用）
     *
     * @param pageable
     * @return
     * @throws Exception
     */
    @GetMapping("/getAllIntegralLogByUserNum")
    public Page<IntegralLogInfo> getAllIntegralLogByUserNum(String userNum,
                                                            @PageableDefault(size = 10) Pageable pageable) throws Exception {
        try {
            Page<IntegralLogInfo> integralLogInfoPage = integralLogService.getAllIntegralLogByStudentNum(userNum,
                    pageable);
            return integralLogInfoPage;
        } catch (Exception e) {
            LOGGER.info("getAllIntegralLogByUserNum:" + e.getMessage());
            return null;
        }
    }

    /**
     * 获取申报中的积分（可能没用）
     *
     * @param pageable
     * @return
     * @throws Exception
     */
    @GetMapping("/getIntegralLogDeclaring")
    public Page<IntegralLogInfo> getIntegralLogDeclaring(@PageableDefault(size = 10) Pageable pageable)
            throws Exception {
        try {
            return integralLogService.getIntegralLogDeclaring(UserUtils.getUser().getUserId(), pageable);
        } catch (Exception e) {
            LOGGER.info("getIntegralLogDeclaring:" + e.getMessage());
            return null;
        }
    }

    /*****************************************
     * 导入导出
     *****************************************/

    /**
     * 积分导入
     *
     * @param file
     * @param baseModel
     * @return
     */
    @ApiOperation("积分导入")
    @GetMapping("loadIntegralLogInfo")
    public List<Object> loadIntegralLogInfo(@RequestParam("file") CommonsMultipartFile file, List<String> baseModel) {
        List<Object> list = null;
        try {
            InputStream inputStream = file.getInputStream();
            if (inputStream == null) {
                throw new Exception("文件为空");
            }
            list = ExcelDocument.upload(inputStream, (String[]) baseModel.toArray(), IntegralLogInfo.class,
                    file.getName());
            integralLogService.saveAll(list);
        } catch (Exception e) {
            LOGGER.info("loadIntegralLogInfo:" + e.getMessage());
            return null;
        }
        return list;
    }

    /**
     * 积分导出
     *
     * @return
     */
    @ApiOperation("积分导出")
    @GetMapping("exportIntegralLog")
    public void exportIntegralLog(HttpServletResponse response, IntegralLogCondition condition,
                                  List<String> baseModel) {
        try {
            List<Object> byProjectNum = integralLogService.listIntegralLog(condition);
            Workbook wk = ExcelDocument.download((String[]) baseModel.toArray(), IntegralLogInfo.class, byProjectNum);
            downLoadExcel(response, wk, "integralLog");
        } catch (Exception e) {
            LOGGER.info("exportIntegralLog:" + e.getMessage());
            try {
                response.getWriter().println("下载失败！");
            } catch (IOException e1) {
            }
        }
    }

    /**
     * 项目导入
     *
     * @param file
     * @param baseModel
     * @return
     */
    @ApiOperation("项目导入")
    @GetMapping("loadProjectInfo")
    public List<Object> loadProjectInfo(@RequestParam("file") CommonsMultipartFile file, List<String> baseModel) {
        List<Object> list = null;
        try {
            InputStream inputStream = file.getInputStream();
            if (inputStream == null) {
                throw new Exception("文件为空");
            }
            list = ExcelDocument.upload(inputStream, (String[]) baseModel.toArray(), ProjectInfo.class, file.getName());
            projectService.saveAll(list);
        } catch (Exception e) {
            LOGGER.info("loadProjectInfo:" + e.getMessage());
            return null;
        }
        return list;
    }

    /**
     * 项目导出
     *
     * @return
     */
    @ApiOperation("项目导出")
    @GetMapping("exportProject")
    public void exportProject(HttpServletResponse response, ProjectCondition condition, List<String> baseModel) {
        try {
            List<Object> byProjectNum = projectService.listProject(condition);
            Workbook wk = ExcelDocument.download((String[]) baseModel.toArray(), ProjectInfo.class, byProjectNum);
            downLoadExcel(response, wk, "project");
        } catch (Exception e) {
            LOGGER.info("exportProject:" + e.getMessage());
            try {
                response.getWriter().println("下载失败！");
            } catch (IOException e1) {
            }
        }
    }

    /**
     * 用户导入
     *
     * @param file
     * @param baseModel
     * @return
     */
    @ApiOperation("用户导入")
    @GetMapping("loadUserInfo")
    public List<Object> loadUserInfo(@RequestParam("file") CommonsMultipartFile file, List<String> baseModel) {
        List<Object> list = null;
        try {
            InputStream inputStream = file.getInputStream();
            if (inputStream == null) {
                throw new Exception("文件为空");
            }
            list = ExcelDocument.upload(inputStream, (String[]) baseModel.toArray(), UserInfo.class, file.getName());
            userService.saveAll(list);
        } catch (Exception e) {
            LOGGER.info("loadUserInfo:" + e.getMessage());
            return null;
        }
        return list;
    }

    /**
     * 用户导出
     *
     * @return
     */
    @ApiOperation("用户导出")
    @GetMapping("exportUser")
    public void exportUser(HttpServletResponse response, UserCondition condition, List<String> baseModel) {
        try {
            List<Object> byProjectNum = userService.listUser(condition);
            Workbook wk = ExcelDocument.download((String[]) baseModel.toArray(), UserInfo.class, byProjectNum);
            downLoadExcel(response, wk, "user");
        } catch (Exception e) {
            LOGGER.info("exportProject:" + e.getMessage());
            try {
                response.getWriter().println("下载失败！");
            } catch (IOException e1) {
            }
        }
    }

    /*****************************************
     * 导入导出
     *****************************************/

    private void downLoadExcel(HttpServletResponse response, Workbook wk, String fileName) throws Exception {
        OutputStream outputStream = null;
        try {
            response.reset();
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            response.setHeader("Connection", "close");
            response.setHeader("Content-Type", "application/octet-stream");
            outputStream = response.getOutputStream();
            wk.write(outputStream);
            outputStream.flush();
        } finally {
            if (outputStream != null) {
                outputStream.close();
            }
        }
    }
}

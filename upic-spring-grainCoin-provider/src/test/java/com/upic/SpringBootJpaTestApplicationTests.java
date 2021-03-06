package com.upic;

import com.upic.condition.GrainCoinLogCondition;
import com.upic.common.base.enums.JugeType;
import com.upic.common.base.enums.JygeTypeEnum;
import com.upic.common.utils.redis.UpicRedisComponent;
import com.upic.common.utils.redis.service.IRedisService;
import com.upic.condition.IntegralLogCondition;
import com.upic.condition.IntegralOperateLogCondition;
import com.upic.condition.PrizeCondition;
import com.upic.dto.*;
import com.upic.enums.IntegralLogStatusEnum;
import com.upic.enums.IntegralLogTypeEnum;
import com.upic.po.IntegralOperateLog;
import com.upic.repository.IntegralLogRepository;
import com.upic.service.GrainCoinLogService;
import com.upic.service.IntegralLogService;
import com.upic.service.IntegralOperateLogService;
import com.upic.service.PrizeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootJpaTestApplication.class)
public class SpringBootJpaTestApplicationTests {
	@Autowired
	private IntegralLogRepository logRepository;

	@Autowired
	private GrainCoinLogService grainCoinLogService;

	@Autowired
	private IntegralLogService integralLogService;

	@Autowired
	private IntegralOperateLogService integralOperateLogService;

	@Autowired
	private PrizeService prizeService;

	@Autowired
	private UpicRedisComponent redisComponent;

	@Autowired
	private IRedisService redisService;

	/**
	 * ************************************** IntegralLog
	 * *****************************************
	 */
	@Test
	public void testSaveIntegralLog() {
		for (int i = 0; i < 20; i++) {
			IntegralLogInfo integralLogInfo = new IntegralLogInfo();
			integralLogInfo.setEvent("Event" + (i + 1));
			integralLogInfo.setIntegral(Math.random() * 10);
			integralLogInfo.setStudent("Student" + i);
			integralLogInfo.setType(IntegralLogTypeEnum.VOLUNTARY_APPLICATION);
			integralLogInfo.setStatus(IntegralLogStatusEnum.PENDING_AUDIT);
			IntegralLogIdInfo integralLogIdInfo = new IntegralLogIdInfo("StudentNum" + i, "ProjectNum" + i);
			integralLogInfo.setIntegralLogId(integralLogIdInfo);
			integralLogService.saveIntegralLog(integralLogInfo);
		}
	}

	@Test
	public void testAllOperation() {
		String apartment = null;
		IntegralLogTypeEnum type = null;
		IntegralLogStatusEnum status = null;
		integralLogService.allOperation(apartment, "", status, type);
	}

	@Test
	public void testWatchIntegral() {
		double score = integralLogService.watchIntegral("StudentNum12");
		System.out.println(score);
	}

	@Test
	public void testGetUserListByProjectNum() {
		PageRequest pageRequest = new PageRequest();
		Page<IntegralLogInfo> integralLogInfoPage = integralLogService.getUserListByProjectNum("ProjectNum0",
				pageRequest);
		System.out.println(integralLogInfoPage.getTotalElements());
		System.out.println(integralLogInfoPage.getTotalPages());
		for (IntegralLogInfo integralLogInfo : integralLogInfoPage.getContent()) {
			System.out.println(integralLogInfo);
		}
	}

	/**
	 * ************************************** Prize
	 * *****************************************
	 */
	@Test
	public void testAddPrize() {
		for (int i = 0; i < 20; i++) {
			PrizeInfo prizeInfo = new PrizeInfo();
			prizeInfo.setPrizeName("PrizeName" + (i + 1));
			prizeInfo.setScore(i + 1);
			prizeInfo.setTitle("Title" + (i + 1));
			prizeInfo.setContent("Content" + (i + 1));
			prizeInfo.setRemark("Remark" + (i + 1));
			prizeService.addPrize(prizeInfo);
		}
	}

	@Test
	public void testUpdatePrize() {
		PrizeInfo prizeInfo = prizeService.getPrizeById(1L);
		prizeInfo.setRemark("1111111111111");
		prizeService.updatePrize(prizeInfo);
	}

	@Test
	public void testSearchPrize() {
		PrizeCondition prizeCondition = new PrizeCondition();
		PageRequest pageRequest = new PageRequest();
		Page<PrizeInfo> prizeInfoPage = prizeService.searchPrizes(prizeCondition, pageRequest);
		System.out.println(prizeInfoPage.getTotalElements());
		System.out.println(prizeInfoPage.getTotalPages());
		for (PrizeInfo prizeInfo : prizeInfoPage.getContent()) {
			System.out.println(prizeInfo);
		}
	}

	/**
	 * ************************************** IntegralOperateLog
	 * *****************************************
	 */
	@Test
	public void testAddIntegralOperateLog() {
		for (int i = 0; i < 20; i++) {
			IntegralOperateLogInfo integralOperateLogInfo = new IntegralOperateLogInfo();
			integralOperateLogInfo.setEvent("Event" + (i + 1));
			integralOperateLogService.addIntegralOperateLog(integralOperateLogInfo);
		}
	}

	@Test
	public void testSearchIntegralOperateLog() {
		IntegralOperateLogCondition integralOperateLogCondition = new IntegralOperateLogCondition();
		PageRequest pageRequest = new PageRequest();
		Page<IntegralOperateLogInfo> integralOperateLogInfoPage = integralOperateLogService
				.searchIntegralOperateLog(integralOperateLogCondition, pageRequest);
		System.out.println(integralOperateLogInfoPage.getTotalElements());
		System.out.println(integralOperateLogInfoPage.getTotalPages());
		for (IntegralOperateLogInfo integralOperateLogInfo : integralOperateLogInfoPage.getContent()) {
			System.out.println(integralOperateLogInfo);
		}
	}

	@Test
	public void testGetByIntegralOperateLogId() {
		IntegralOperateLogInfo integralOperateLogInfo = integralOperateLogService.getByIntegralOperateLogId(1L);
		System.out.println(integralOperateLogInfo.toString());
	}

	/**
	 * ************************************** GrainCoinLog
	 * *****************************************
	 */
	// @Test
	// public void testExchangePrize() {
	// for (long i = 4L; i < 7L; i++) {
	// long prizeId = i;
	// GrainCoinLogInfo grainCoinLogInfo = new GrainCoinLogInfo();
	// grainCoinLogInfo.setEvent("Event" + (i - 3));
	// grainCoinLogService.exchangePrize(prizeId, grainCoinLogInfo);
	// }
	// }
	@Test
	public void testWatchGrainCoin() {
		String studentNum = "1422110108";
		double score = grainCoinLogService.watchGrainCoin(studentNum);
		System.out.println(score);
	}

	@Test
	public void testGetAllIntegralLogByStudentNum() {
		String studentNum = "StudentNum2";
		PageRequest pageRequest = new PageRequest();
		Page<IntegralLogInfo> integralLogInfoPage = integralLogService.getAllIntegralLogByStudentNum(studentNum,
				pageRequest);
		System.out.println(integralLogInfoPage.getTotalElements());
		System.out.println(integralLogInfoPage.getTotalPages());
		for (IntegralLogInfo integralLogInfo : integralLogInfoPage.getContent()) {
			System.out.println(integralLogInfo);
		}
	}

	/*********************************
	 * redis
	 *********************************************/

	@Test
	public void testRedis() {
		ExecutorService newCachedThreadPool = Executors.newCachedThreadPool();
		Thread[] t = new Thread[10];
		for (int i = 0; i < 10; i++) {
			Thread t1 = new Thread(() -> {
				for (int j = 0; j < 5000; j++) {
					// System.out.println(Thread.currentThread().getName() + ":" +
					// redisComponent.increment("1001"));
					redisComponent.increment("1001");
				}
			});
			t[i] = t1;
		}
		long startTime = System.currentTimeMillis();
		Stream.of(t).parallel().forEach((a) -> newCachedThreadPool.submit(a));
		try {
			newCachedThreadPool.awaitTermination(5, TimeUnit.SECONDS);
			System.out.println("总共消费时间:" + (System.currentTimeMillis() - startTime));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testInit() {
		System.out.println("result:" + redisComponent.init("1001"));
		// redisComponent.set("dtz", "superman");
	}

	@Test
	public void testRedisAdd() {
		ExecutorService newCachedThreadPool = Executors.newCachedThreadPool();
		Thread[] t = new Thread[10];
		for (int i = 0; i < 10; i++) {
			Thread t1 = new Thread(() -> {
				for (int j = 0; j < 5; j++) {
					// System.out.println(Thread.currentThread().getName() + ":" +
					// redisComponent.increment("1001"));
					IntegralLogInfo i1 = new IntegralLogInfo();
					i1.setIntegralLogId(new IntegralLogIdInfo(Thread.currentThread().getName(), "1001"));
					IntegralLogInfo signUp = integralLogService.signUp(i1);
					if (signUp == null) {
						System.out.println(Thread.currentThread().getName() + "：" + "第" + j + "次抢票失败");
					} else {
						System.out.println(
								Thread.currentThread().getName() + "：" + "第" + j + "次抢票成功,并且票号为:" + signUp.getField1());
					}
				}
			});
			t[i] = t1;
		}
		long startTime = System.currentTimeMillis();
		Stream.of(t).parallel().forEach((a) -> newCachedThreadPool.submit(a));
		try {
			newCachedThreadPool.awaitTermination(5, TimeUnit.SECONDS);
			System.out.println("总共消费时间:" + (System.currentTimeMillis() - startTime));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testAdd() {
		redisComponent.decrement("1001");
		// List<IntegralLog> l=new ArrayList<>();
		// for(int i=0;i<2;i++) {
		// IntegralLog integralLog = new IntegralLog();
		// IntegralLogId integralLogId = new IntegralLogId();
		// integralLogId.setStudentNum("1422110108");
		// integralLogId.setProjectNum("1001");
		// integralLog.setIntegralLogId(integralLogId);
		// l.add(integralLog);
		// }
		// logRepository.save(l);
		// System.out.println(redisComponent.putIfAbsent("1001hash", "142211", 1+""));
	}

	@Test
	public void testIntegralLogSearchBar() {
		String status = "ALREADY_SIGN_UP";
		String keyword = "1";
		PageRequest pageRequest = new PageRequest();
		Page<IntegralLogInfo> integralLogInfoPage = integralLogService
				.integralLogSearchBar(IntegralLogStatusEnum.ALREADY_SIGN_UP, keyword, pageRequest);
		integralLogInfoPage.getContent().forEach(t -> System.out.println(t.toString()));
	}

	@Test
	public void testUpdateIntegralLogStatus() {
		List<IntegralLogIdInfo> integralLogIdInfoList = new ArrayList<IntegralLogIdInfo>();
		IntegralLogIdInfo integralLogIdInfoOne = new IntegralLogIdInfo();
		IntegralLogIdInfo integralLogIdInfoTwo = new IntegralLogIdInfo();
		integralLogIdInfoOne.setProjectNum("PROJECT001");
		integralLogIdInfoOne.setStudentNum("1522110240");
		integralLogIdInfoTwo.setProjectNum("PROJECT027");
		integralLogIdInfoTwo.setStudentNum("1522110240");
		integralLogIdInfoList.add(integralLogIdInfoOne);
		integralLogIdInfoList.add(integralLogIdInfoTwo);

		IntegralLogStatusEnum status = IntegralLogStatusEnum.ALREADY_SIGN_UP;

		System.out.println(integralLogService.updateIntegralLogStatus(integralLogIdInfoList, status));
	}

	@Test
	public void testGetGrainCoinLog() {
		GrainCoinLogCondition grainCoinLogCondition = new GrainCoinLogCondition();
		PageRequest pageRequest = new PageRequest();
		Page<GrainCoinLogInfo> grainCoinLogInfoPage = grainCoinLogService.searchPrizeByCondition(grainCoinLogCondition,
				pageRequest);
		System.out.println(grainCoinLogInfoPage.getTotalElements());
		System.out.println(grainCoinLogInfoPage.getTotalPages());
		for (GrainCoinLogInfo grainCoinLogInfo : grainCoinLogInfoPage.getContent()) {
			System.out.println(grainCoinLogInfo);
		}
	}

	@Test
	public void testListIntegralLog() {
		IntegralLogCondition integralLogCondition = new IntegralLogCondition();
		List<Object> a = integralLogService.listIntegralLog(integralLogCondition);
		System.out.println(a.toString());
	}

	@Test
	public void testGetIntegralLogBySql() {
		List<String> statusList = new ArrayList<>();
		List<String> projectCategoryList = new ArrayList<>();
		PageRequest pageRequest = new PageRequest();
		statusList.add("PENDING_AUDIT");
		projectCategoryList.add("1创新实践积分（选修积分）");

		// Page<IntegralLogInfo> integralLogInfoPage =
		// integralLogService.getIntegralLogBySql(statusList, projectCategoryList,
		// pageRequest);
		// integralLogInfoPage.getContent().forEach(t ->
		// System.out.println(t.toString()));
	}

	@Test
	public void testGetIntegralLogByStudentNum() {
		PageRequest pageRequest = new PageRequest();
		Page<IntegralLogInfo> integralLogInfoPage = integralLogService.getIntegralLogByStudentNum("1522110238",
				pageRequest);
		for (IntegralLogInfo integralLogInfo : integralLogInfoPage.getContent()) {
			System.out.println(integralLogInfo);
		}
	}

	@Test
	public void testHash() {
		redisService.putIfAbsent("SQ16201300751514435762208hash", "1422110108", "12");
		redisService.putIfAbsent("SQ16201300751514435762208hash", "1422110109", "13");
		redisService.putIfAbsent("SQ16201300751514435762208hash", "1422110110", "14");
		redisService.putIfAbsent("SQ16201300751514435762208hash", "1422110111", "15");
	}

	@Test
	public void testCondi() {
		IntegralLogCondition condi = new IntegralLogCondition();
		List<String> statusList = new ArrayList<String>();
		statusList.add("PENDING_AUDIT_FINAL");
		// List<String> projectCategoryList = s.getProjectCategoryList();
		String rank = "1";

		// if(rank.equals("3")) {
		// String colloge = s.getCollegeAli();
		// condi.setCollegeOtherName(colloge);
		// }
		//
		List<Map<String, Object>> orList = new ArrayList<Map<String, Object>>();
		// if (rank.equals("2")) {
		// Map<String, Object> maps = new HashMap<String, Object>();
		// for (String projectCategory : projectCategoryList) {
		// maps.put(new String("projectCategory"), projectCategory);
		// }
		// orList.add(maps);
		// }
		Map<String, Object> mapStatus = new HashMap<String, Object>();
		for (String status : statusList) {
			IntegralLogStatusEnum enum1 = getEnum(status);
			if (enum1 == null) {
				continue;
			}
			mapStatus.put(new String("status"), new JugeType(JygeTypeEnum.EQUAL, enum1));
		}
		orList.add(mapStatus);
		condi.setOrList(orList);
		Page<IntegralLogInfo> searchIntegralLog = integralLogService.searchIntegralLog(condi, new PageRequest());
		System.out.println(searchIntegralLog.getContent().size());
	}

	public IntegralLogStatusEnum getEnum(String status) {
		IntegralLogStatusEnum[] values = IntegralLogStatusEnum.values();
		IntegralLogStatusEnum temp = null;
		for (IntegralLogStatusEnum v : values) {
			if (status.equals(v.name())) {
				temp = v;
			}
		}
		return temp;
	}
	
	public static void main(String[] args) {
		System.out.println(IntegralLogStatusEnum.PENDING_AUDIT.toString());
	}
}

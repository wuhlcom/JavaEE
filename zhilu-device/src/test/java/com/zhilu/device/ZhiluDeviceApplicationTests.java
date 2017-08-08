package com.zhilu.device;

import com.zhilu.device.bean.*;
import com.zhilu.device.bean.primary.TblIotDevice;
import com.zhilu.device.repository.*;
import com.zhilu.device.repository.primary.TblIotDevRepo;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootConfiguration()
public class ZhiluDeviceApplicationTests {

	@Autowired
	private TblIotDevRepo tblIotDevRepo;
//	@Autowired
//	private MessageRepository messageRepository;

	@Before
	public void setUp() {
	}

	@Test
	public void test() throws Exception {

//		userRepository.save(new User("aaa", 10));
//		userRepository.save(new User("bbb", 20));
//		userRepository.save(new User("ccc", 30));
//		userRepository.save(new User("ddd", 40));
//		userRepository.save(new User("eee", 50));

//		Assert.assertEquals(5, userRepository.findAll().size());

//		messageRepository.save(new Message("o1", "aaaaaaaaaa"));
//		messageRepository.save(new Message("o2", "bbbbbbbbbb"));
//		messageRepository.save(new Message("o3", "cccccccccc"));
//
//		Assert.assertEquals(3, messageRepository.findAll().size());
		List<TblIotDevice> dev =tblIotDevRepo.findAll();
	System.out.println(dev);

	}


}

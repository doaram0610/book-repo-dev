package com.sds.book.domain.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sds.book.domain.model.User;
import com.sds.book.domain.repository.UserRepository;

import lombok.RequiredArgsConstructor;



//기능을 정의하고 트랜잭션을 관리할 수 있다.

@RequiredArgsConstructor  //finall 이 붙어있는 함수의 생성자를 만들어 준다. 찾아바라
@Service
public class UserService {

	private final UserRepository userRepository;

	//로그인
	@Transactional(readOnly = true) //readOnly=true 는 JPA의 변경감지 기능을 비활성화해서 내트랜잭션 안에서는 다른 트랜잭션이 UPDATE 를 해도 내 트랜젝션안의 데이터를 유지시켜준다.
	public User login(String userId, String userPwd) {
		 	return userRepository.login(userId, userPwd);		 
	}
	
	public User Save(User user) {
		return userRepository.save(user);
	}
	
	@Transactional(readOnly = true) //readOnly=true 는 JPA의 변경감지 기능을 비활성화해서 내트랜잭션 안에서는 다른 트랜잭션이 UPDATE 를 해도 내 트랜젝션안의 데이터를 유지시켜준다.
	//단 INSERT 를 한 것에 대해서는 못 막아주긴 한다. 그래두 UPDATE 의 정합성을 유지해주니까 꼭 사용하자
	public User Select(Long id) {
			return userRepository.findById(id).orElseThrow(
//					new Supplier<IllegalArgumentException>() {
//						public IllegalArgumentException get() {
//							return new IllegalArgumentException("아이디를 확인해라");
//						}
//					}
					()->new IllegalArgumentException("아이디를 확인해라")  //헛.. 머야 되자나.. 
					); //여기에 람다식 넣으면 왜 에러나는지 함 찾아보자
	}
	
	public java.util.List<User>  ListAll() {
		return userRepository.findAll();
	}
	
	@Transactional  //서비스 함수가 종료될때 commit 할지 rollback 할지 트랜잭션 관리하겠다.
	public User Update(Long id, User login) {
		
		User userEntity = userRepository.findById(id).orElseThrow(()->new IllegalArgumentException("아이디를 확인해라"));
		userEntity.setUserId(login.getUserId());
		userEntity.setUserPwd(login.getUserPwd());
		userEntity.setUserName(login.getUserName());
		userEntity.setProvider(login.getProvider());
		userEntity.setProviderId(login.getProviderId());
		userEntity.setRole(login.getRole());
		return userEntity;  //여기에 왜 .save 를 안썼냐면 더티체킹을 했기때문이다. findById 를 통해서 DB에서 데이터를 가져오고 트랜잭션안에서 갖고 있는 데이터를 변경했다.
	}  //그렇게 되면 마지막 리턴시(함수종료)에 변경된데이터를 받으면서 영속화된데이터를 db 로 갱신(flush)하면서 commit 된다. 이것이 더티체킹이다.
	
	@Transactional
	public String Delete(Long id) {
		userRepository.deleteById(id);   //오류가 발생하면 익센션을 타니까 신경쓰지말고 그냥 둔다.
		 return "ok";
	}	
	
}

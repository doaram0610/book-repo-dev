package com.sds.book.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sds.book.domain.model.Book;
import com.sds.book.domain.service.BookService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor //final 이라고 된 객체의 생성자를 자동으로 만들어준다.
@Controller
@RequestMapping(value = "/manager")
public class BookController {

	private final BookService bookService;
	
	//관리자의 index 화면
	@GetMapping({"", "/", "/books"})
	public String goBooks(){
		return "books";
	}	

	@GetMapping({"/book"})	//여기가 기본 인덱스홈
	public ResponseEntity<?> findAll(){
		return new ResponseEntity<>(bookService.ListAll(), HttpStatus.OK);
	}
	
	//리턴타입을 ResponseEntity 로 사용하면 리턴코드와 리턴데이터를 함께 넘겨줄수 있으니 이것을 사용해라
	//security (라이브러리를 적용하면) - CORS정책을 갖고 있다.(시큐리티가 CORS를 해제해줘야 한다.)
	@PostMapping("/book")
	public ResponseEntity<?> save(@RequestBody Book book){  //@RequestBody는 json으로 받겠다는 뜻
		return new ResponseEntity<>(bookService.Save(book), HttpStatus.OK);
	}
		
//	@CrossOrigin(origins = "*", allowedHeaders = "*") // 컨트롤러에서 설정 //외부에서오는 자바스크립트 요청을 허용해준다.
	@GetMapping("/book/{id}")
	public ResponseEntity<?> findById(@PathVariable Long id){
		return new ResponseEntity<>(bookService.Select(id), HttpStatus.OK);
	}	

	@PutMapping("/book/{id}")
	public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Book book){
		return new ResponseEntity<>(bookService.Update(id, book), HttpStatus.OK);
	}	
	
	//제네릭 ? 란 : JDK 1.5부터 도입된 제네릭을 사용하면 컴파일 시에 미리 타입이 정해지므로, 타입 검사나 타입 변환과 같은 번거로운 작업을 생략할 수 있게 됩니다.
	//<?> : 타입 변수에 모든 타입을 사용할 수 있음
	@DeleteMapping("/book/{id}")
	public ResponseEntity<?> deleteById(@PathVariable Long id){
		return new ResponseEntity<>(bookService.Delete(id), HttpStatus.OK);
	}	
}

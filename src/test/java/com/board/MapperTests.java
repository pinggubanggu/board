package com.board;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.CollectionUtils;

import com.board.domain.BoardDTO;
import com.board.mapper.BoardMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest		
public class MapperTests {

	@Autowired
	private BoardMapper boardMapper;
	
	@Test
	public void testOfInsert() {
		//given
		BoardDTO params = new BoardDTO();
		params.setTitle("1번 게시글 제목");
		params.setContent("1번 게시글 내용");
		params.setWriter("테스터");
		
		//when
		int result = boardMapper.insertBoard(params);
		
		//then
		log.info("결과={}", result);
	}
	
	@Test
	public void testOfSelectDetail() {
		BoardDTO board = boardMapper.selectBoardDetail((long)1);
		
		try {
			ObjectMapper objectMapper =	new ObjectMapper();
			objectMapper.registerModule(new JavaTimeModule());
			String boardJson = objectMapper.writeValueAsString(board);
			
			System.out.println("=======================");
			System.out.println(boardJson);
			System.out.println("=======================");
			
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testOfUpdate() {
		BoardDTO params = new BoardDTO();
		params.setTitle("1번 게시글 제목을 수정합니다.");
		params.setContent("1번 게시글 내용을 수정합니다.");
		params.setWriter("홍길동");
		params.setIdx((long)1);
		
		int result = boardMapper.updateBoard(params);
		if(result == 1) {
			BoardDTO board = boardMapper.selectBoardDetail((long)1);
			try {
				 String boardJson = new ObjectMapper().registerModule(new JavaTimeModule())
				  					.writeValueAsString(board);

				 log.info("boardJson={}", boardJson);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		}
	}
		
		@Test
		public void testOfDelete() {
			//given
			
			//when
			int result = boardMapper.deleteBoard((long)1);
			
			//then
			if(result == 1) {
				BoardDTO board = boardMapper.selectBoardDetail((long)1);
				try {
					String boardJson = new ObjectMapper().registerModule(new JavaTimeModule())
		  												 .writeValueAsString(board);
					System.out.println("=================");
					log.info("boardJson={}", boardJson);
					System.out.println("=================");
				} catch (JsonProcessingException e) {
					e.printStackTrace();
				}
				
			}
				
		}
		
		@Test
		public void testMultipleInsert() {
			for (int i= 2; i <=50; i++) {
				BoardDTO params = new BoardDTO();
				params.setTitle(i + "번 게시글 제목");
				params.setContent(i +"번 게시글 내용");
				params.setWriter(i +"번 게시글 작성자");
				boardMapper.insertBoard(params);
			}
		}
		
		@Test
		public void testSelectList() {
			int boardTotalCount = boardMapper.selectBoardTotalCount();
			if(boardTotalCount > 0) {
				List<BoardDTO> boardList = boardMapper.selectBoardList();
				if(CollectionUtils.isEmpty(boardList) == false) {
					for	(BoardDTO board : boardList) {
						log.info("===================");
						log.info("board.Title={}", board.getTitle());
						log.info("board.Content={}", board.getContent());
						log.info("board.Writer={}", board.getWriter());
						log.info("===================");
					}
				}
			}
		}
	}
	
	
	


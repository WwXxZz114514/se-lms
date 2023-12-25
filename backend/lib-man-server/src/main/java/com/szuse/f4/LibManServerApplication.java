package com.szuse.f4;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@MapperScan("com.szuse.f4.mapper")
public class LibManServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibManServerApplication.class, args);
	}

}

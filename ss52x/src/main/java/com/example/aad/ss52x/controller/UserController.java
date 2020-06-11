package com.example.aad.ss52x.controller;

import java.security.Principal;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

   @GetMapping("/")
   public String index() {
	   System.out.println("index page security details ::" + SecurityContextHolder.getContext().getAuthentication());
	   
      return "index";
   }
}

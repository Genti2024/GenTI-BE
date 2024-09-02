package com.gt.genti;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.ActiveProfiles;

import com.gt.genti.config.ScheduleConfig;
import com.gt.genti.discord.DiscordAppender;

@SpringBootTest
@ActiveProfiles({"secret", "common", "test"})
@ComponentScan(basePackages = "com.gt.genti", excludeFilters = {
	@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {ScheduleConfig.class}),
	@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {DiscordAppender.class})})
public abstract class BaseTest {

}
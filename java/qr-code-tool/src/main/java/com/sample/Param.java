package com.sample;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class Param {
	@NotEmpty(message = "content is required.")
	private String content;

	@NotNull(message = "width is required.")
	private Long width;

	@NotNull(message = "height is required.")
	private Long height;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Long getWidth() {
		return width;
	}

	public void setWidth(Long width) {
		this.width = width;
	}

	public Long getHeight() {
		return height;
	}

	public void setHeight(Long height) {
		this.height = height;
	}
	
}

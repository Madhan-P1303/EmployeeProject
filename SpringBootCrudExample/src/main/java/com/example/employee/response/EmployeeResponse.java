package com.example.employee.response;

import com.example.employee.enumulator.EmployeeEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeResponse {
	public EmployeeEnum responseStatus;
	public String successMessage;
	public String errorMessage;
	public int code;
	public Object data;
	public EmployeeEnum getResponseStatus() {
		return responseStatus;
	}
	public void setResponseStatus(EmployeeEnum responseStatus) {
		this.responseStatus = responseStatus;
	}
	public String getSuccessMessage() {
		return successMessage;
	}
	public void setSuccessMessage(String successMessage) {
		this.successMessage = successMessage;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	
}

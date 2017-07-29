package com.medical.ratrofitinterface;

import com.medical.model.AppointmentTimeResponse;
import com.medical.model.DeleteAppointmentResponse;
import com.medical.model.DisplayDoctProfileResponse;
import com.medical.model.PatientAppointmentResponse;
import com.medical.model.SearchDoctResponse;
import com.medical.model.SpecialistCategoryResponse;
import com.medical.model.SpecialistSubcatResponse;

import retrofit.Callback;
import retrofit.http.EncodedQuery;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;

public interface DoctFin {

//	@FormUrlEncoded
//	@POST("/UserLogin")
//	public void checkUser(@Field("UserName") String userName,
//			@Field("Password") String userPassword,@Field("DeviceType") String deviceType,@Field("TokenId") String tokenId,
//			Callback<UserRegistrationRespose> cb);

	@GET("/categoryList")
	public void getSpecialistCategoryList(
			Callback<SpecialistCategoryResponse> cb);

	@GET("/subCategoryList")
	public void getSubSpecialistSubCategoryList(@EncodedQuery("Id") int Id,
			Callback<SpecialistSubcatResponse> cb);

	@FormUrlEncoded
	@POST("/SearchDoctor")
	public void getDoctorList(@Field("Latitude") Double Latitude,
			@Field("Longitude") Double Longitude,
			@Field("appointmentDate") String appointmentDate,
			@Field("CategoryId") int CategoryId,
			@Field("SubCategoryId") int SubCategoryId,
			Callback<SearchDoctResponse> cb);

	@GET("/DoctorInformationDetail")
	public void getDoctorProfile(@EncodedQuery("DocInfoId") int DocInfoId,
			Callback<DisplayDoctProfileResponse> cb);

	@GET("/PatientAppointmentlist")
	public void getPatientAppointmentList(@EncodedQuery("UserId") int UserId,
			Callback<PatientAppointmentResponse> cb);

	@GET("/appointmentTimeListOfDoctor")
	public void getAppointmentTimeList(
			@EncodedQuery("DocInfoId") int RegistrationId,
			@EncodedQuery("AppointmentDate") String AppointmentDate,
			Callback<AppointmentTimeResponse> cb);

	
	@GET("/DeleteAppointment")
	public void deletePatientAppointment(
			@EncodedQuery("AppointmentId") int AppointmentId,
			Callback<DeleteAppointmentResponse> cb);

//	@GET("/NewDoctorList")
//	public void getMedicalTeamList(Callback<MedicalTeamResponse> cb);
//
//	@FormUrlEncoded
//	@POST("/NewForgotPassword")
//	public void getForgotPasswrod(@Field("Email") String Email,
//			Callback<ForgotPasswordResponse> cb);
//
//	@FormUrlEncoded
//	@POST("/ChangePassword")
//	public void changePassword(@Field("UserId") int Userid,
//			@Field("OldPassword") String oldPassword,
//			@Field("NewPassword") String newPassword,
//			Callback<ChangePasswordModel> cb);

}

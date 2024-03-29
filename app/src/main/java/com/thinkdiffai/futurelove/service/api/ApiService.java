package com.thinkdiffai.futurelove.service.api;

import com.thinkdiffai.futurelove.model.EventVideo;
import com.thinkdiffai.futurelove.model.EventVideoParent;
import com.thinkdiffai.futurelove.model.ImageModel;
import com.thinkdiffai.futurelove.model.ListCommentOfEventModel;
import com.thinkdiffai.futurelove.model.ListVideoModel;
import com.thinkdiffai.futurelove.model.ListVideoModel2;
import com.thinkdiffai.futurelove.model.ListVideoModel2Parent;
import com.thinkdiffai.futurelove.model.ListVideoModelParent;
import com.thinkdiffai.futurelove.model.DetailEventList;
import com.thinkdiffai.futurelove.model.DetailEventListParent;
import com.thinkdiffai.futurelove.model.EventHomeDto;
import com.thinkdiffai.futurelove.model.GetVideoSwapResponse;
import com.thinkdiffai.futurelove.model.GetYourVideoSwapModel;
import com.thinkdiffai.futurelove.model.IpNetworkModel;
import com.thinkdiffai.futurelove.model.Login;
import com.thinkdiffai.futurelove.model.Sukien2Image;
import com.thinkdiffai.futurelove.model.VideoSwapResponseParent;
import com.thinkdiffai.futurelove.model.comment.CommentList;
import com.thinkdiffai.futurelove.model.comment.DetailUser;
import com.thinkdiffai.futurelove.model.comment.EventsUser.EventsUser;
import com.thinkdiffai.futurelove.model.comment.UserComment;
import com.thinkdiffai.futurelove.model.comment.eacheventcomment.EachEventCommentsList;
import com.thinkdiffai.futurelove.view.fragment.Swap2Image;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    @FormUrlEncoded
    @POST("https://sakaivn.online/changeavatar/" + "{id_user}")
    Call<Object> changeAvatar(
            @Path("id_user") int id_user,
            @Header("Authorization") String authorization,
            @Field("link_img") String link_img,
            @Field("check_img") String check_img
    );

    @GET("https://databaseswap.mangasocial.online/profile/" + "{id_user}")
    Call<DetailUser> getDetailUser(
            @Path("id_user") int id_user
    );

    @GET(Server.URI_PAIRING)
    Call<Object> postEvent(
            @Header(Server.KEY_HEADER1) String imageLink1,
            @Header(Server.KEY_HEADER2) String imageLink2,
            @Query("device_them_su_kien") String deviceThemSuKien,
            @Query("ip_them_su_kien") String ipThemSuKien,
            @Query("id_user") long userId,
            @Query("ten_nam") String tenNam,
            @Query("ten_nu") String tenNu
    );

    @FormUrlEncoded
    @POST(Server.URI_LIST_COMMENT_BY_EVENT_ID)
    Call<Object> postComment(
            @Field("id_user") int id_user,
            @Field("noi_dung_cmt") String nd_cmt,
            @Field("device_cmt") String device_cmt,
            @Field("id_toan_bo_su_kien") String id_event,
            @Field("so_thu_tu_su_kien") int stt_event,
            @Field("ipComment") String ip_cmt,
            @Field("imageattach") String imageattach,
            @Field("id_user_cmt") int id_user_cmt,
            @Field("location") String location,
            @Field("link_imagesk") String link_img

    );
    @GET(Server.GET_VIDEO_SWAP + "{page}")
    Call<VideoSwapResponseParent> getVideoSwapRespone(
            @Query("device_them_su_kien") String device_add_event,
            @Query("ip_them_su_kien") String ip_add_event,
            @Query("id_user") int id_user,
            @Query("src_img") String src_image
    );

    @GET("https://databaseswap.mangasocial.online/lovehistory/listvideo/" + "{page}")
    Call<ListVideoModel2> getListVideo(
            @Path("page") int id,
            @Query("category") int id_categories
    );

    @GET("https://videoswap.mangasocial.online/getdata/genvideo")
    Call<EventVideo> getVideoSwap(
            @Header("Authorization") String authorization,
            @Query("id_video") String id_video,
            @Query("device_them_su_kien") String device,
            @Query("ip_them_su_kien") String ip,
            @Query("id_user") int id_user,
            @Query("image") String link,
            @Query("ten_video") String name_video
    );
    @GET(Server.GET_IMAGE_UPLOAD_BY_USER + "{id_user}")
    Call<ImageModel> getListImageUpload(
            @Path("id_user") int id_user,
            @Query("type") String type
    );

    @GET(Server.GET_ALL_EVENT_BY_USER_ID + "{page}")
    Call<DetailEventListParent> getAllEventByUserId(
            @Path("page") long id

    );

    @GET(Server.GET_ALL_VIDEO_BY_ID_USER+"{id_user}")
    Call<ListVideoModelParent> getVidCreateByUser(
            @Path("id_user") int id_user,
            @Query("trang") int page
    );

    @GET(Server.GET_ALL_COMMENT_OF_EVENT + "{so_thu_tu_sk}")
    Call<ListCommentOfEventModel> getAllComment(
            @Path("so_thu_tu_sk") int stt_su_kien,
            @Query("id_toan_bo_su_kien") String id_toan_bo_su_kien,
            @Query("id_user") int id_user
    );

    @GET(Server.URI_CREATE_IMPLICIT_DATA)
    Call<Object> postImplicitEvent(
            @Query("device_them_su_kien") String deviceThemSuKien,
            @Query("ip_them_su_kien") String ipThemSuKien,
            @Query("id_user") long userId,
            @Query("ten_nam") String tenNam,
            @Query("ten_nu") String tenNu
    );


    @GET("https://videoswap.mangasocial.online/getdata/genvideo")
    Call<GetVideoSwapResponse> getUrlVideoSwap(
            @Header("Authorization") String authorization,
            @Query("id_video") int idVideo,
            @Query("device_them_su_kien") String deviceThemSuKien,
            @Query("ip_them_su_kien") String ipThemSuKien,
            @Query("id_user") int idUser,
            @Query("image") String image,
            @Query("ten_video") String tenVideo
    );

    @Multipart
    @POST(Server.UPLOAD_IMAGE +"{id_user}")
    Call<String> uploadImage(
            @Path("id_user") int id_user,
            @Query("type") String fileType,
            @Part MultipartBody.Part src_img
    );

    @GET("https://thinkdiff.us/getdata/swap/2/image")
    Call<Sukien2Image> GetResultImageSwap(
            @Header("Authorization") String authorization,
            @Header("link1") String link_1,
            @Header("link2") String link_2,
            @Query("device_them_su_kien") String deviceAddEvent,
            @Query("ip_them_su_kien") String ipAddEvent,
            @Query("id_user") int id_user


    );

    @Multipart
    @POST("https://videoswap.mangasocial.online/getdata/genvideo/swap/imagevid")
    Call<GetYourVideoSwapModel> PostVid (
            @Header("Authorization") String authorization,
            @Query("device_them_su_kien") String deviceThemSuKien,
            @Query("ip_them_su_kien") String ipThemSuKien,
            @Query("id_user") int id_user,
            @Query("src_img") String src_img,
            @Part MultipartBody.Part src_vid
    );


    @GET(Server.URI_GET_NETWORK_STATUS)
    Call<IpNetworkModel> getIpApiResponse();

    @GET(Server.URI_LIST_EVENT_HOME + "page")
    Call<List<List<EventHomeDto>>> getListAllEventHome(@Path("page") long id);

    @GET(Server.URI_LIST_EVENT_HOME + "{page}" )
    Call<DetailEventListParent> getEventListForHome(@Path("page") long id,
                                                    @Query("id_user")int id_user);

    @GET(Server.URI_LIST_EVENT_TIMELINE + "{id}")
    Call<DetailEventList> getListEventDetail(@Path("id") long id);

    // Get all comments of each event
    @GET(Server.URI_LIST_COMMENT_BY_EVENT_ID + "{so_thu_tu_su_kien}")
    Call<EachEventCommentsList> getListCommentByEventId(
            @Path("so_thu_tu_su_kien") int soThuTuSuKien,
            @Query("id_toan_bo_su_kien") long idToanBoSuKien,
            @Query("id_user") int idUser
    );

    @GET(Server.URI_LIST_COMMENT_NEW + "{id}")
    Call<CommentList> getListCommentNew(@Path("id") int id,
                                        @Query("id_user") int id_user);


    @FormUrlEncoded
    @POST(Server.URI_POST_EVENT_TIMELINE)
    Call<Object> postListEventDetail(@Field("id") String id,
                                     @Field("link_da_swap") String linkdaswap,
                                     @Field("link_nam_chua_swap") String linknamchuaswap,
                                     @Field("link_nam_goc") String linknamgoc,
                                     @Field("link_nu_chua_swap") String linknuchuaswap,
                                     @Field("link_nu_goc") String link_nu_goc,
                                     @Field("noi_dung_su_kien") String noidungsukien,
                                     @Field("real_time") String realtime,
                                     @Field("so_thu_tu_su_kien") String sothutusukien,
                                     @Field("ten_su_kien") String tensukien,
                                     @Field("tom_Luoc_Text") String tom_Luoc_Text);
    @FormUrlEncoded
    @POST(Server.URI_POST_COMMENT)
    Call<Object> postDataComment(@Field("id_user") int idUser,
                                 @Field("noi_dung_cmt") String content,
                                 @Field("device_cmt") String device,
                                 @Field("id_toan_bo_su_kien") String idSummary,
                                 @Field("so_thu_tu_su_kien") int soThuTuSuKien,
                                 @Field("ipComment") String ip,
                                 @Field("imageattach") String imagEattach);
    @FormUrlEncoded
    @POST(Server.URI_LOG_IN)
    Call<Login> login(
            @Field("email_or_username") String email,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST(Server.URI_SIGN_UP)
    Call<Object> signUp(
            @Field("email") String email,
            @Field("password") String password,
            @Field("user_name") String userName,
            @Field("link_avatar") String linkAvatar,
            @Field("ip_register") String registerIp,
            @Field("device_register") String deviceRegister
    );
    // get detail user
    @GET(Server.URI_PROFILE_USER + "{page}")
    Call<DetailUser> getProfileUser(@Path("page") long id);


    // GET comments user
    @GET(Server.URI_COMMENTS_USER + "{page}")
    Call<UserComment> getCommentUser(@Path("page") long id);

    // get events theo user
    @GET(Server.URI_EVENTS_USER + "{page}")
    Call<EventsUser> getEventUser(@Path("page") long id);

    @GET(Server.URI_DELETE_ACCOUNT + "{user_id}")
    Call<Object> deleteAccount(@Path("user_id") long id);

    // CHANGE PASSWORD
    @FormUrlEncoded
    @POST(Server.URI_CHANGE_PASSWORD+"{page}")
    Call<Object> Change_Password(
            @Path("page")long id,
//            @Field("email_or_username") String email,
            @Field("oldPassword") String oldpassword,
            @Field("newPassword") String newpassword
    );

}


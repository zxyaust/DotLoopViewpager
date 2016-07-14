package z.dotloopviewpager.other;

/**
 * Created by z on 2016/5/31.
 */
public interface URLConstant {
    String HOST = "http://www.shixi.com/";
    String POSITION_SEARCH = HOST + "appsearch";
    String CITYS = HOST + "tool/appcitys";
    String ACTIVITY = HOST + "appactivity/view";
    String REGISTER_CODE = HOST + "tool/appcode/appreg";
    String REGISTER = HOST + "appregist";
    String LOGIN = HOST + "applogin";
    String FIND_PASSWORD_CODE = HOST + "tool/appcode/appmfind";
    String CHANGE_PASSWORD = HOST + "apppwd/find";
    String DYNAMIC_CODE = HOST + "tool/appcode/apppwd";
    String ACTIVITY_LIST = HOST + "appactivity";
    String FEEDBACK = HOST + "appfeedback";
    String CREATE_RESUME = HOST + "appresume/create";
    String EDUCATION = HOST + "tool/appdatas/QS_education";
    String GET_COLLECTION = HOST + "appcompanys/getcolljobs";
    String LOOK_RESUME = HOST + "appresume/index";
    String CHANGE_SELFDESC = HOST + "appresume/specialty";
    String SKILLS_EDIT = HOST + "appresume/skill";
    String BASE_INFO = HOST + "appresume/info";

    //职位搜索中职位分类
    String QS_POSITION_TYPE = HOST + "tool/appdatas/position_type";
    //职位搜索中学历要求
    String QS_EDUCATION = HOST + "tool/appdatas/QS_education";
    //职位搜索中行业要求

    String QS_TRADE1 = HOST + "tool/appdatas/QS_trade";
    String Add_PROJECT = HOST + "appresume/project";
    String EDIT_TRADE = HOST + "tool/appdatas/QS_trade";
    String CHANGE_JOBEXP = HOST + "appresume/work";
    String ADD_EDUCATION = HOST + "appresume/education";




    String QS_TRADE = HOST + "tool/apptrades";
    //轮播图
    String PICTURE_LUNBO = HOST + "appad";
    //职位详情
    String POSITION_DETAIL = HOST + "appcompanys/jobshow";
    //收藏职位
    String COLLECT_JOB = HOST + "appcompanys/collejobs";
    //取消收藏职位
    String UNCOLLECT_JOB = HOST + "appcompanys/unfavorite";
    //投简历
    String SEND_RESUME = HOST + "appcompanys/castresume";
    //公司详情
    String COMPANY_DETAIL = HOST + "appcompanys/companyshow";
    //删除类目
    String DELETE = HOST + "appresume/delete";
    //消息
    String MESSAGE = HOST + "appmsg";

    //热门职位
    String HOT_POSITION = HOST + "tool/apphot?count=5";

    String DELETE_COLLECT = HOST + "appcompanys/unfavorite";
    String LOGIN_OUT = HOST + "applogin/logout";
    //求职意向
    String JOB_INTENT = HOST + "appresume/intent";
    String JOB_NATURE = HOST + "tool/appdatas/QS_jobs_nature";
    //清除所有收藏职位
    String CLEAN_COLLECT_JOBS = HOST + "appcompanys/cleanfavorite";
    //头像
    String AVATAR = HOST+"appresume/avatar";

}

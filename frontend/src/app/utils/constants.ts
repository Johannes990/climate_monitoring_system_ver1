// frontend URL routing paths
export const LOGIN_URL_PATH = "/login/login";
export const REGISTER_URL_PATH = "/login/register";
export const DASHBOARD_URL_PATH = "/dashboard/dashboardMain";
export const DASHBOARD_CONTROLPARAM_URL_PATH = "/dashboard/controlParameters";

//backend API request paths
export const LOGIN_QUERY_PATH = "/login";
export const LOGOUT_QUERY_PATH = "/logout";
export const REGISTER_QUERY_PATH = "/register";
export const PROTECTED_QUERY_PATH = "/protected";
export const CONTROL_PARAMS_QUERY_PATH = "/controlparams/"
export const CONTROL_PARAMS_ALL_QUERY_PATH = CONTROL_PARAMS_QUERY_PATH + "all";
export const CONTROL_PARAMS_ADD_QUERY_PATH = CONTROL_PARAMS_QUERY_PATH + "add";
export const CONTROL_PARAMS_DELETE_PATH = CONTROL_PARAMS_QUERY_PATH + "delete/";
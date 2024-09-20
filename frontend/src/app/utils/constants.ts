// frontend URL routing paths
export const BASE_LOGIN_PATH = "/login/"
export const LOGIN_URL_PATH = BASE_LOGIN_PATH + "login";
export const REGISTER_URL_PATH = BASE_LOGIN_PATH + "register";
export const BASE_DASHBOARD_PATH = "/dashboard/"
export const DASHBOARD_URL_PATH = BASE_DASHBOARD_PATH + "dashboardMain";
export const DASHBOARD_CONTROLPARAM_URL_PATH = BASE_DASHBOARD_PATH + "controlParameters";
export const DASHBOARD_LOCATIONS_URL_PATH = BASE_DASHBOARD_PATH + "locations";
export const DASHBOARD_SENSORS_URL_PATH = BASE_DASHBOARD_PATH + "sensors";

//backend API request paths
export const LOGIN_QUERY_PATH = "/login";
export const LOGOUT_QUERY_PATH = "/logout";
export const REGISTER_QUERY_PATH = "/register";
export const PROTECTED_QUERY_PATH = "/protected";

export const CONTROL_PARAMS_QUERY_PATH = "/controlparams/"
export const CONTROL_PARAMS_ALL_QUERY_PATH = CONTROL_PARAMS_QUERY_PATH + "all";
export const CONTROL_PARAMS_ADD_QUERY_PATH = CONTROL_PARAMS_QUERY_PATH + "add";
export const CONTROL_PARAMS_DELETE_PATH = CONTROL_PARAMS_QUERY_PATH + "delete/";

export const LOCATIONS_QUERY_PATH = "/locations/";
export const LOCATIONS_ALL_QUERY_PATH = LOCATIONS_QUERY_PATH + "all";
export const LOCATIONS_BY_DESCRIPTION_CONTAINING_QUERY_PATH = LOCATIONS_QUERY_PATH + "bydescriptioncontaining/";
export const LOCATIONS_DELETE_QUERY_PATH = LOCATIONS_QUERY_PATH + "delete/";
export const LOCATIONS_ADD_QUERY_PATH = LOCATIONS_QUERY_PATH + "add";

export const SENSORS_QUERY_PATH = "/sensors/";
export const SENSORS_ALL_QUERY_PATH = SENSORS_QUERY_PATH + "all";
export const SENSORS_BY_PASSKEY_QUERY_PATH = SENSORS_QUERY_PATH + "bypasskey/";
export const SENSORS_BY_LOCATION_QUERY_PATH = SENSORS_QUERY_PATH + "bylocation/";
export const SENSORS_BY_DEVICECODE_QUERY_PATH = SENSORS_QUERY_PATH + "bydevicecode/";
export const SENSORS_ADD_QUERY_PATH = SENSORS_QUERY_PATH + "add";
export const SENSORS_DELETE_QUERY_PATH = SENSORS_QUERY_PATH + "delete/";
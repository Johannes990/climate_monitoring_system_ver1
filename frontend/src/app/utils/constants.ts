// frontend URL routing paths
export const BASE_LOGIN_PATH = "/login/"
export const LOGIN_URL_PATH = BASE_LOGIN_PATH + "login";
export const REGISTER_URL_PATH = BASE_LOGIN_PATH + "register";
export const BASE_DASHBOARD_PATH = "/dashboard/"
export const DASHBOARD_URL_PATH = BASE_DASHBOARD_PATH + "dashboardMain";
export const DASHBOARD_CONTROLPARAM_URL_PATH = BASE_DASHBOARD_PATH + "controlParameters";
export const DASHBOARD_LOCATIONS_URL_PATH = BASE_DASHBOARD_PATH + "locations";
export const DASHBOARD_SENSORS_URL_PATH = BASE_DASHBOARD_PATH + "sensors";
export const DASHBOARD_SENSOR_READINGS_URL_PATH = BASE_DASHBOARD_PATH + "readings";
export const DASHBOARD_NOTIFICATIONS_PATH = BASE_DASHBOARD_PATH + "notifications";

// backend API request paths
// login and registering
export const LOGIN_QUERY_PATH = "/login";
export const LOGOUT_QUERY_PATH = "/logout";
export const REGISTER_QUERY_PATH = "/register";
export const PROTECTED_QUERY_PATH = "/protected";

// control parameters
export const CONTROL_PARAMS_QUERY_PATH = "/controlparams/";
export const CONTROL_PARAMS_ALL_QUERY_PATH = CONTROL_PARAMS_QUERY_PATH + "all";
export const CONTROL_PARAMS_ADD_QUERY_PATH = CONTROL_PARAMS_QUERY_PATH + "add";
export const CONTROL_PARAMS_DELETE_PATH = CONTROL_PARAMS_QUERY_PATH + "delete/";

// locations
export const LOCATIONS_QUERY_PATH = "/locations/";
export const LOCATIONS_ALL_QUERY_PATH = LOCATIONS_QUERY_PATH + "all";
export const LOCATIONS_BY_DESCRIPTION_CONTAINING_QUERY_PATH = LOCATIONS_QUERY_PATH + "bydescriptioncontaining/";
export const LOCATIONS_DELETE_QUERY_PATH = LOCATIONS_QUERY_PATH + "delete/";
export const LOCATIONS_ADD_QUERY_PATH = LOCATIONS_QUERY_PATH + "add";

// sensors
export const SENSORS_QUERY_PATH = "/sensors/";
export const SENSORS_ALL_QUERY_PATH = SENSORS_QUERY_PATH + "all";
export const SENSORS_BY_PASSKEY_QUERY_PATH = SENSORS_QUERY_PATH + "bypasskey/";
export const SENSORS_BY_LOCATION_QUERY_PATH = SENSORS_QUERY_PATH + "bylocation/";
export const SENSORS_BY_DEVICECODE_QUERY_PATH = SENSORS_QUERY_PATH + "bydevicecode/";
export const SENSORS_ADD_QUERY_PATH = SENSORS_QUERY_PATH + "add";
export const SENSORS_DELETE_QUERY_PATH = SENSORS_QUERY_PATH + "delete/";

// readings
export const READINGS_QUERY_PATH = "/readings/";
export const READINGS_ALL_QUERY_PATH = READINGS_QUERY_PATH + "all";
export const READINGS_BY_SENSOR_QUERY_PATH = READINGS_QUERY_PATH + "bysensor/";

// notification types
export const NOTIFICATION_TYPES_QUERY_PATH = "/notificationtypes/";
export const NOTIFICATION_TYPES_ALL_QUERY_PATH = NOTIFICATION_TYPES_QUERY_PATH + "all";
export const NOTIFICATION_TYPES_ADD_QUERY_PATH = NOTIFICATION_TYPES_QUERY_PATH + "add";
export const NOTIFICATION_TYPES_DELETE_QUERY_PATH = NOTIFICATION_TYPES_QUERY_PATH + "delete";

// actions
export const ACTIONS_QUERY_PATH = "/actions/";
export const ACTIONS_ALL_QUERY_PATH = ACTIONS_QUERY_PATH + "all";
export const ACTIONS_ADD_QUERY_PATH = ACTIONS_QUERY_PATH + "add/";
export const ACTIONS_BY_USER_QUERY_PATH = ACTIONS_QUERY_PATH + "byuser/";
export const ACTIONS_BEFORE_QUERY_PATH = ACTIONS_QUERY_PATH + "before/";
export const ACTIONS_AFTER_QUERY_PATH = ACTIONS_QUERY_PATH + "after/";

// notifications
export const NOTIFICATIONS_QUERY_PATH = "/notifications/";
export const NOTIFICATIONS_ALL_QUERY_PATH = NOTIFICATIONS_QUERY_PATH + "all";
export const NOTIFICATIONS_ACTIVE_QUERY_PATH = NOTIFICATIONS_QUERY_PATH + "active";
export const NOTIFICATIONS_BY_NOTIFICATION_TYPE_QUERY_PATH = NOTIFICATIONS_QUERY_PATH + "bynotificationtype/";
export const NOTIFICATIONS_BY_SENSOR_QUERY_PATH = NOTIFICATIONS_QUERY_PATH + "bysensor/";
export const NOTIFICATIONS_BY_ACTION_TAKEN_QUERY_PATH = NOTIFICATIONS_QUERY_PATH + "byactiontaken/";
export const NOTIFICATIONS_BY_CONDITIONS_RESOLVED_QUERY_PATH = NOTIFICATIONS_QUERY_PATH + "byconditionsresolved/";
export const NOTIFICATIONS_BEFORE_QUERY_PATH = NOTIFICATIONS_QUERY_PATH + "before/";
export const NOTIFICATIONS_AFTER_QUERY_PATH = NOTIFICATIONS_QUERY_PATH + "after/";

// graph colors
export const TEMPERATURE_READING_COLOR = "#101010";
export const HUMIDITY_READING_COLOR = "#00AB9C";
export const TEMPERATURE_LIMIT_COLOR = "#FF0000";
export const HUMIDITY_LIMIT_COLOR = "#FF0000"
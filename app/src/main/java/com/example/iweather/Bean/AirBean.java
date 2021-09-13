package com.example.iweather.Bean;

import java.util.List;
public class AirBean {
    private List<HeWeather6Bean> HeWeather6;
    public List<HeWeather6Bean> getHeWeather6() {
        return HeWeather6;
    }
    public void setHeWeather6(List<HeWeather6Bean> HeWeather6) {
        this.HeWeather6 = HeWeather6;
    }
    public static class HeWeather6Bean {
        private AirNowCityBean air_now_city;
        private BasicBean basic;
        private String status;
        private UpdateBean update;
        private List<AirNowStationBean> air_now_station;
        public AirNowCityBean getAir_now_city() {
            return air_now_city;
        }
        public void setAir_now_city(AirNowCityBean air_now_city) {
            this.air_now_city = air_now_city;
        }
        public BasicBean getBasic() {
            return basic;
        }
        public void setBasic(BasicBean basic) {
            this.basic = basic;
        }
        public String getStatus() {
            return status;
        }
        public void setStatus(String status) {
            this.status = status;
        }
        public UpdateBean getUpdate() {
            return update;
        }
        public void setUpdate(UpdateBean update) {
            this.update = update;
        }
        public List<AirNowStationBean> getAir_now_station() {
            return air_now_station;
        }
        public void setAir_now_station(List<AirNowStationBean> air_now_station) {
            this.air_now_station = air_now_station;
        }
        public static class AirNowCityBean {
            private String aqi;
            private String co;
            private String main;
            private String no2;
            private String o3;
            private String pm10;
            private String pm25;
            private String pub_time;
            private String qlty;
            private String so2;
            public String getAqi() {
                return aqi;
            }
            public void setAqi(String aqi) {
                this.aqi = aqi;
            }
            public String getCo() {
                return co;
            }
            public void setCo(String co) {
                this.co = co;
            }
            public String getMain() {
                return main;
            }
            public void setMain(String main) {
                this.main = main;
            }
            public String getNo2() {
                return no2;
            }
            public void setNo2(String no2) {
                this.no2 = no2;
            }
            public String getO3() {
                return o3;
            }
            public void setO3(String o3) {
                this.o3 = o3;
            }
            public String getPm10() {
                return pm10;
            }
            public void setPm10(String pm10) {
                this.pm10 = pm10;
            }
            public String getPm25() {
                return pm25;
            }
            public void setPm25(String pm25) {
                this.pm25 = pm25;
            }
            public String getPub_time() {
                return pub_time;
            }
            public void setPub_time(String pub_time) {
                this.pub_time = pub_time;
            }
            public String getQlty() {
                return qlty;
            }
            public void setQlty(String qlty) {
                this.qlty = qlty;
            }
            public String getSo2() {
                return so2;
            }
            public void setSo2(String so2) {
                this.so2 = so2;
            }
        }
        public static class BasicBean {
            private String cid;
            private String location;
            private String parent_city;
            private String admin_area;
            private String cnty;
            private String lat;
            private String lon;
            private String tz;
            public String getCid() {
                return cid;
            }
            public void setCid(String cid) {
                this.cid = cid;
            }
            public String getLocation() {
                return location;
            }
            public void setLocation(String location) {
                this.location = location;
            }
            public String getParent_city() {
                return parent_city;
            }
            public void setParent_city(String parent_city) {
                this.parent_city = parent_city;
            }
            public String getAdmin_area() {
                return admin_area;
            }
            public void setAdmin_area(String admin_area) {
                this.admin_area = admin_area;
            }
            public String getCnty() {
                return cnty;
            }
            public void setCnty(String cnty) {
                this.cnty = cnty;
            }
            public String getLat() {
                return lat;
            }
            public void setLat(String lat) {
                this.lat = lat;
            }
            public String getLon() {
                return lon;
            }
            public void setLon(String lon) {
                this.lon = lon;
            }
            public String getTz() {
                return tz;
            }
            public void setTz(String tz) {
                this.tz = tz;
            }
        }
        public static class UpdateBean {
            private String loc;
            private String utc;
            public String getLoc() {
                return loc;
            }
            public void setLoc(String loc) {
                this.loc = loc;
            }
            public String getUtc() {
                return utc;
            }
            public void setUtc(String utc) {
                this.utc = utc;
            }
        }
        public static class AirNowStationBean {
            private String air_sta;
            private String aqi;
            private String asid;
            private String co;
            private String lat;
            private String lon;
            private String main;
            private String no2;
            private String o3;
            private String pm10;
            private String pm25;
            private String pub_time;
            private String qlty;
            private String so2;
            public String getAir_sta() {
                return air_sta;
            }
            public void setAir_sta(String air_sta) {
                this.air_sta = air_sta;
            }
            public String getAqi() {
                return aqi;
            }
            public void setAqi(String aqi) {
                this.aqi = aqi;
            }
            public String getAsid() {
                return asid;
            }
            public void setAsid(String asid) {
                this.asid = asid;
            }
            public String getCo() {
                return co;
            }
            public void setCo(String co) {
                this.co = co;
            }
            public String getLat() {
                return lat;
            }
            public void setLat(String lat) {
                this.lat = lat;
            }
            public String getLon() {
                return lon;
            }
            public void setLon(String lon) {
                this.lon = lon;
            }
            public String getMain() {
                return main;
            }
            public void setMain(String main) {
                this.main = main;
            }
            public String getNo2() {
                return no2;
            }
            public void setNo2(String no2) {
                this.no2 = no2;
            }
            public String getO3() {
                return o3;
            }
            public void setO3(String o3) {
                this.o3 = o3;
            }
            public String getPm10() {
                return pm10;
            }
            public void setPm10(String pm10) {
                this.pm10 = pm10;
            }
            public String getPm25() {
                return pm25;
            }
            public void setPm25(String pm25) {
                this.pm25 = pm25;
            }
            public String getPub_time() {
                return pub_time;
            }
            public void setPub_time(String pub_time) {
                this.pub_time = pub_time;
            }
            public String getQlty() {
                return qlty;
            }
            public void setQlty(String qlty) {
                this.qlty = qlty;
            }
            public String getSo2() {
                return so2;
            }
            public void setSo2(String so2) {
                this.so2 = so2;
            }
        }
    }
}

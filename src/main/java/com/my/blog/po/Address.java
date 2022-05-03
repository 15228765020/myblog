package com.my.blog.po;

public   class Address { //{"country":"中国","province":"福建","city":"None","county":"None","isp":"阿里巴巴"}

        private  String address;

        public class content{
            private String address;

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public class addressDetail{
                private String city;
                private String cityCode;
                private String province;

                public String getCity() {
                    return city;
                }

                public void setCity(String city) {
                    this.city = city;
                }

                public String getCityCode() {
                    return cityCode;
                }

                public void setCityCode(String cityCode) {
                    this.cityCode = cityCode;
                }

                public String getProvince() {
                    return province;
                }

                public void setProvince(String province) {
                    this.province = province;
                }
            }

            public class point{
                private String x;

                private String y;

                public String getX() {
                    return x;
                }

                public void setX(String x) {
                    this.x = x;
                }

                public String getY() {
                    return y;
                }

                public void setY(String y) {
                    this.y = y;
                }
            }
        }

        private String status;

        public String getAddress() {
            //CN|四川省|成都市|None|None|100|100
            //修改成"四川省成都市"

            return splitStr();
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        private String splitStr(){
            String[] adrArr = this.address.split("\\|");

            return adrArr[1]+adrArr[2];

        }
    }
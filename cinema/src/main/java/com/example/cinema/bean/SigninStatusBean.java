package com.example.cinema.bean;

import java.util.List;

public class SigninStatusBean {
        /**
         * cinemasList : []
         * headPic : http://172.17.8.100/images/movie/head_pic/bwjy.jpg
         * integral : 0
         * movieList : []
         * nickName : 15794311454
         * phone : 16220336248
         * userSignStatus : 1
         */

        private String headPic;
        private int integral;
        private String nickName;
        private String phone;
        private int userSignStatus;
        private List<?> cinemasList;
        private List<?> movieList;

        public String getHeadPic() {
            return headPic;
        }

        public void setHeadPic(String headPic) {
            this.headPic = headPic;
        }

        public int getIntegral() {
            return integral;
        }

        public void setIntegral(int integral) {
            this.integral = integral;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public int getUserSignStatus() {
            return userSignStatus;
        }

        public void setUserSignStatus(int userSignStatus) {
            this.userSignStatus = userSignStatus;
        }

        public List<?> getCinemasList() {
            return cinemasList;
        }

        public void setCinemasList(List<?> cinemasList) {
            this.cinemasList = cinemasList;
        }

        public List<?> getMovieList() {
            return movieList;
        }

        public void setMovieList(List<?> movieList) {
            this.movieList = movieList;
        }
}

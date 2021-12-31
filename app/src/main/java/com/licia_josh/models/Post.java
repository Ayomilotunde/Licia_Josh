package com.licia_josh.models;


public class Post {
    private String uid, username, writeUp, postImage, profileImage, postid, celebration, postVideo, itemName, itemPrice, bg;
    private String date, time, Comment, userComment;
    private String giftImage, proofImage, shoutOutImage, trend;
    private String fullName, email, phoneNumber, userName, status, heading;


    public Post(String uid, String username, String writeUp, String postImage,
                String profileImage, String postid, String celebration,
                String postVideo, String itemName, String itemPrice,
                String counter, String bg, String date, String time,
                String comment, String userComment, String giftImage,
                String proofImage, String shoutOutImage, String trend,
                String fullName, String email, String phoneNumber, String status, String heading) {
        this.uid = uid;
        this.username = username;
        this.writeUp = writeUp;
        this.postImage = postImage;
        this.profileImage = profileImage;
        this.postid = postid;
        this.celebration = celebration;
        this.postVideo = postVideo;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.bg = bg;
        this.date = date;
        this.time = time;
        Comment = comment;
        this.userComment = userComment;
        this.giftImage = giftImage;
        this.proofImage = proofImage;
        this.shoutOutImage = shoutOutImage;
        this.trend = trend;
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.status = status;
        this.heading = heading;
    }

    public Post() {
    }


    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getBg() {
        return bg;
    }

    public void setBg(String bg) {
        this.bg = bg;
    }

    public String getTrend() {
        return trend;
    }

    public void setTrend(String trend) {
        this.trend = trend;
    }

    public String getShoutOutImage() {
        return shoutOutImage;
    }

    public void setShoutOutImage(String shoutOutImage) {
        this.shoutOutImage = shoutOutImage;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProofImage() {
        return proofImage;
    }

    public void setProofImage(String proofImage) {
        this.proofImage = proofImage;
    }

    public String getGiftImage() {
        return giftImage;
    }

    public void setGiftImage(String giftImage) {
        this.giftImage = giftImage;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getPostVideo() {
        return postVideo;
    }

    public void setPostVideo(String postVideo) {
        this.postVideo = postVideo;
    }

    public String getCelebration() {
        return celebration;
    }

    public void setCelebration(String celebration) {
        this.celebration = celebration;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getWriteUp() {
        return writeUp;
    }

    public void setWriteUp(String writeUp) {
        this.writeUp = writeUp;
    }

    public String getPostImage() {
        return postImage;
    }

    public void setPostImage(String postImage) {
        this.postImage = postImage;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getPostid() {
        return postid;
    }

    public void setPostid(String postid) {
        this.postid = postid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }

    public String getUserComment() {
        return userComment;
    }

    public void setUserComment(String userComment) {
        this.userComment = userComment;
    }
}

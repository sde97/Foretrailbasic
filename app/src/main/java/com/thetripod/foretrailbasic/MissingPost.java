package com.thetripod.foretrailbasic;

public class MissingPost {

    private String name,location,lastSeen,missingFrom,missingId,mImageUrl;

    public MissingPost() {
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }

    public MissingPost(String name, String location, String lastSeen, String missingFrom, String missingId, String mImageUrl) {
        this.name = name;
        this.location = location;
        this.lastSeen = lastSeen;
        this.missingFrom = missingFrom;
        this.missingId = missingId;
        this.mImageUrl = mImageUrl;
    }

    @Override
    public String toString() {
        return "NewMissingEntry{" +
                "name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", lastSeen='" + lastSeen + '\'' +
                ", missingFrom='" + missingFrom + '\'' +
                ", missingId='" + missingId + '\'' +
                '}';
    }

    public String getMissingId() {
        return missingId;
    }

    public void setMissingId(String missingId) {
        this.missingId = missingId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLastSeen() {
        return lastSeen;
    }

    public void setLastSeen(String lastSeen) {
        this.lastSeen = lastSeen;
    }

    public String getMissingFrom() {
        return missingFrom;
    }

    public void setMissingFrom(String missingFrom) {
        this.missingFrom = missingFrom;
    }

}

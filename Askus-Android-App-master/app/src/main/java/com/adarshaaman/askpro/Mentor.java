package com.adarshaaman.askpro;

public class Mentor {
    private String phone ;
    private  String name ;
    private String coaching ;
    private String year ;
    private String college;
    private int jeeadv; //ranks , 0 if not aapplicable
    private int jeem ;
    private float cbse ;
    private String hometown ;
    private float rating ;
    private int numRating;
    private String imageurl;
    private boolean isAdvPro; //true when it appears on top
    private boolean isMainPro;
    private boolean isBoardPro;
    private String description ;
    private  int audioprice ;
    private  int videoprice;
   // public int priceperslot ;


    private float motivation ; //ratings
    private float accuracy ;
    private float physics ;
    private float chemistry;
    private float maths;
    private float temperament ;
    private float studypattern ;

    public Mentor(){}

    public Mentor(String phone, String name, String coaching,
                  String year, String college, int jeeadv, int jeem, float cbse,
                  String hometown, float rating, int numRating, String imageurl, String description,
                  float motivation, float accuracy, float physics, float chemistry, float maths,
                  float temperament, float studypattern,boolean isAdvPro, boolean isMainPro , boolean isBoardPro,
           int audioprice, int videoprice
           // , int priceperslot
    ) {
        this.phone = phone;
        this.name = name;
        this.coaching = coaching;
        this.year = year;
        this.college = college;
        this.jeeadv = jeeadv;
        this.jeem = jeem;
        this.cbse = cbse;
        this.hometown = hometown;
        this.rating = rating;
        this.numRating = numRating;
        this.imageurl = imageurl;
        this.description = description;
        this.motivation = motivation;
        this.accuracy = accuracy;
        this.physics = physics;
        this.chemistry = chemistry;
        this.maths = maths;
        this.temperament = temperament;
        this.studypattern = studypattern;
        this.isAdvPro= isAdvPro;
        this.isMainPro= isMainPro;
        this.isBoardPro= isBoardPro;
        this.audioprice= audioprice;
        this.videoprice = videoprice;
        //this.priceperslot= priceperslot;
    }

    public boolean isAdvPro() {
        return isAdvPro;
    }

    public void setAdvPro(boolean advPro) {
        isAdvPro = advPro;
    }

    public boolean isMainPro() {
        return isMainPro;
    }

    public void setMainPro(boolean mainPro) {
        isMainPro = mainPro;
    }

    public boolean isBoardPro() {
        return isBoardPro;
    }

    public void setBoardPro(boolean boardPro) {
        isBoardPro = boardPro;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCoaching() {
        return coaching;
    }

    public void setCoaching(String coaching) {
        this.coaching = coaching;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public int getJeeadv() {
        return jeeadv;
    }

    public void setJeeadv(int jeeadv) {
        this.jeeadv = jeeadv;
    }

    public int getJeem() {
        return jeem;
    }

    public void setJeem(int jeem) {
        this.jeem = jeem;
    }

    public float getCbse() {
        return cbse;
    }

    public void setCbse(float cbse) {
        this.cbse = cbse;
    }

    public String getHometown() {
        return hometown;
    }

    public void setHometown(String hometown) {
        this.hometown = hometown;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public int getNumRating() {
        return numRating;
    }

    public void setNumRating(int numRating) {
        this.numRating = numRating;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getMotivation() {
        return motivation;
    }

    public void setMotivation(float motivation) {
        this.motivation = motivation;
    }

    public float getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(float accuracy) {
        this.accuracy = accuracy;
    }

    public float getPhysics() {
        return physics;
    }

    public void setPhysics(float physics) {
        this.physics = physics;
    }

    public float getChemistry() {
        return chemistry;
    }

    public void setChemistry(float chemistry) {
        this.chemistry = chemistry;
    }

    public float getMaths() {
        return maths;
    }

    public void setMaths(float maths) {
        this.maths = maths;
    }

    public float getTemperament() {
        return temperament;
    }

    public void setTemperament(float temperament) {
        this.temperament = temperament;
    }

    public float getStudypattern() {
        return studypattern;
    }

    public void setStudypattern(float studypattern) {
        this.studypattern = studypattern;
    }

    public int getAudioprice() {
        return audioprice;
    }

    public void setAudioprice(int audioprice) {
        this.audioprice = audioprice;
    }

    public int getVideoprice() {
        return videoprice;
    }

    public void setVideoprice(int videoprice) {
        this.videoprice = videoprice;
    }


    // public int getPriceperslot() {
   //     return priceperslot;
    //}

  //  public void setPriceperslot(int priceperslot) {
  //      this.priceperslot = priceperslot;
  //  }
}

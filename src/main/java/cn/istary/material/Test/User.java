package cn.istary.material.Test;

import javax.inject.Inject;

public class User {

    String name;

    @Inject
    public User(){
        this.name = "Jason";
    }

}

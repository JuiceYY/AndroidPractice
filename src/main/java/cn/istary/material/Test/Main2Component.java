package cn.istary.material.Test;

import dagger.Component;

@Component(modules = TextViewModule.class)
public interface Main2Component {

    void inject(Main2Activity activity);

}

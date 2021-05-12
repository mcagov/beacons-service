package uk.gov.mca.beacons.service.mappers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNull.nullValue;

import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ModelPatcherTest {

  private ModelPatcher<FakeModel> patcher;

  @BeforeEach
  public void before() {
    patcher =
      new ModelPatcherFactory<FakeModel>()
        .getModelPatcher()
        .withMapping(FakeModel::getFirstDate, FakeModel::setFirstDate)
        .withMapping(FakeModel::getSecondDate, FakeModel::setSecondDate)
        .withMapping(FakeModel::getThirdDate, FakeModel::setThirdDate)
        .withMapping(FakeModel::getFirstString, FakeModel::setFirstString)
        .withMapping(FakeModel::getSecondString, FakeModel::setSecondString)
        .withMapping(FakeModel::getThirdString, FakeModel::setThirdString)
        .withMapping(FakeModel::getFirstInt, FakeModel::setFirstInt)
        .withMapping(FakeModel::getSecondInt, FakeModel::setSecondInt)
        .withMapping(FakeModel::getThirdInt, FakeModel::setThirdInt)
        .withMapping(FakeModel::getFirstBoolean, FakeModel::setFirstBoolean)
        .withMapping(FakeModel::getSecondBoolean, FakeModel::setSecondBoolean)
        .withMapping(FakeModel::getThirdBoolean, FakeModel::setThirdBoolean);
  }

  @Test
  public void shouldMapUpdatedValuesWithoutChangingTheRest() {
    var oldModel = new FakeModel();
    oldModel.setFirstDate(LocalDateTime.of(1983, 3, 13, 13, 13, 0));
    oldModel.setSecondString("The A String");
    oldModel.setThirdInt(3);
    oldModel.setFirstBoolean(false);
    var updateModel = new FakeModel();
    updateModel.setFirstDate(LocalDateTime.of(1992, 11, 8, 1, 2, 0));
    updateModel.setSecondString("The B String");
    updateModel.setThirdInt(33);
    updateModel.setFirstBoolean(true);

    var result = patcher.patchModel(oldModel, updateModel);
    assertThat(result.firstDate, is(updateModel.firstDate));
    assertThat(result.secondDate, is(nullValue()));
    assertThat(result.thirdDate, is(nullValue()));
    assertThat(result.firstString, is(nullValue()));
    assertThat(result.secondString, is(updateModel.secondString));
    assertThat(result.thirdString, is(nullValue()));
    assertThat(result.firstInt, is(0));
    assertThat(result.secondInt, is(0));
    assertThat(result.thirdInt, is(updateModel.thirdInt));
    assertThat(result.firstBoolean, is(true));
    assertThat(result.secondBoolean, is(nullValue()));
    assertThat(result.thirdBoolean, is(nullValue()));
  }

  @Test
  public void shouldMapNewValuesWithoutChangingTheExisting() {
    var oldModel = new FakeModel();
    oldModel.setFirstDate(LocalDateTime.of(1983, 3, 13, 13, 13, 0));
    oldModel.setSecondString("The A String");
    oldModel.setThirdInt(3);
    oldModel.setFirstBoolean(false);
    var updateModel = new FakeModel();
    updateModel.setSecondDate(LocalDateTime.of(1992, 11, 8, 1, 2, 0));
    updateModel.setThirdString("The B String");
    updateModel.setFirstInt(11);
    updateModel.setSecondBoolean(true);

    var result = patcher.patchModel(oldModel, updateModel);
    assertThat(result.firstDate, is(oldModel.firstDate));
    assertThat(result.secondDate, is(updateModel.secondDate));
    assertThat(result.thirdDate, is(nullValue()));
    assertThat(result.firstString, is(nullValue()));
    assertThat(result.secondString, is(oldModel.secondString));
    assertThat(result.thirdString, is(updateModel.thirdString));
    assertThat(result.firstInt, is(updateModel.firstInt));
    assertThat(result.secondInt, is(0));
    assertThat(result.thirdInt, is(oldModel.thirdInt));
    assertThat(result.firstBoolean, is(false));
    assertThat(result.secondBoolean, is(true));
    assertThat(result.thirdBoolean, is(nullValue()));
  }

  class FakeModel {

    private String firstString;

    public String getFirstString() {
      return firstString;
    }

    public Boolean getSecondBoolean() {
      return secondBoolean;
    }

    public void setSecondBoolean(Boolean secondBoolean) {
      this.secondBoolean = secondBoolean;
    }

    public Boolean getFirstBoolean() {
      return firstBoolean;
    }

    public void setFirstBoolean(Boolean firstBoolean) {
      this.firstBoolean = firstBoolean;
    }

    public Boolean getThirdBoolean() {
      return thirdBoolean;
    }

    public void setThirdBoolean(Boolean thirdBoolean) {
      this.thirdBoolean = thirdBoolean;
    }

    public void setFirstString(String firstString) {
      this.firstString = firstString;
    }

    public String getSecondString() {
      return secondString;
    }

    public void setSecondString(String secondString) {
      this.secondString = secondString;
    }

    public String getThirdString() {
      return thirdString;
    }

    public void setThirdString(String thirdString) {
      this.thirdString = thirdString;
    }

    public LocalDateTime getFirstDate() {
      return firstDate;
    }

    public void setFirstDate(LocalDateTime firstDate) {
      this.firstDate = firstDate;
    }

    public LocalDateTime getSecondDate() {
      return secondDate;
    }

    public void setSecondDate(LocalDateTime secondDate) {
      this.secondDate = secondDate;
    }

    public LocalDateTime getThirdDate() {
      return thirdDate;
    }

    public void setThirdDate(LocalDateTime thirdDate) {
      this.thirdDate = thirdDate;
    }

    public int getFirstInt() {
      return firstInt;
    }

    public void setFirstInt(int firstInt) {
      this.firstInt = firstInt;
    }

    public int getSecondInt() {
      return secondInt;
    }

    public void setSecondInt(int secondInt) {
      this.secondInt = secondInt;
    }

    public int getThirdInt() {
      return thirdInt;
    }

    public void setThirdInt(int thindInt) {
      this.thirdInt = thindInt;
    }

    private String secondString;
    private String thirdString;
    private LocalDateTime firstDate;
    private LocalDateTime secondDate;
    private LocalDateTime thirdDate;
    private int firstInt;
    private int secondInt;
    private int thirdInt;
    private Boolean firstBoolean;
    private Boolean secondBoolean;
    private Boolean thirdBoolean;
  }
}

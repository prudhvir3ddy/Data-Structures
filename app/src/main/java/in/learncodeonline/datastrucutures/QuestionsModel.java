package in.learncodeonline.datastrucutures;

import android.os.Parcel;
import android.os.Parcelable;

public class QuestionsModel implements Parcelable {

    public static final Creator<QuestionsModel> CREATOR = new Creator<QuestionsModel>() {
        @Override
        public QuestionsModel createFromParcel(Parcel in) {
            return new QuestionsModel(in);
        }

        @Override
        public QuestionsModel[] newArray(int size) {
            return new QuestionsModel[size];
        }
    };
    String question, answer;

    public QuestionsModel() {
    }

    protected QuestionsModel(Parcel in) {
        question = in.readString();
        answer = in.readString();
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(question);
        dest.writeString(answer);
    }
}

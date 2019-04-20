package com.yezz.company.yezzclub.lData;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.RadioButton;
import android.widget.TextView;

/**
 * Class to validate different entries in Android
 *  @author: <a href="mailto:rjoserivas@gmail.com">Ramón Rivas</a>
 */
public class Validate {
    private Context context;
    /**
     * Constant value for validate entries required
     * */
    public static final String REQUIRED="required";
    /**
     * Constant value for validate entries integer numbers
     * */
    public static final String NUMBER="number";
    /**
     * Constant value for validate entries numbers with decimal
     * */
    public static final String DECIMAL="decimal";
    /**
     * Constant value for validate entries alphabet
     * */
    public static final String ALPHA="alpha";
    /**
     * Constant value for validate entries only letter
     * */
    public static final String LETTER="letter";

    /**
     *  class constructor
     *  @param context Application context
     * */
    public Validate(Context context) {
        this.context=context;
    }

    /**
     *  Validate input data
     *  @param control input Control
     *  @param rules Validation rules, these rules must be separated by bars "|", The rules may be: required|number|decimal|alpha|letter
     *  @return: false if validation fail
     * */
    public boolean checkValidate(@NonNull RadioButton control, @NonNull String rules){
        return this.checkValidate(this.getValueFromControl(control),rules.split("|"));
    }

    /**
     *  Validate input data
     *  @param control Array the input Control
     *  @param rules Validation rules, equal rule to all input, these rules must be separated by bars "|", The rules may be: required|number|decimal|alpha|letter
     *  @return: false if validation fail
     * */
    public boolean checkValidate(@NonNull RadioButton[] control, @NonNull String rules){
        for (int i=0; i<control.length;i++){
            if(!this.checkValidate(this.getValueFromControl(control[i]),rules.split("|"))){
                return false;
            }
        }
        return true;
    }

    /**
     *  Validate input data
     *  @param control Array the input Control
     *  @param rules Array[n][1] Validation rules, these rules must be separated by bars "|", The rules may be: required|number|decimal|alpha|letter
     *  @return: false if validation fail
     * */
    public boolean checkValidate(@NonNull RadioButton[] control, @NonNull String[][] rules){
        for (int i=0; i<control.length;i++){
            if(!this.checkValidate(this.getValueFromControl(control[i]),rules[i][0].split("|"))){
                return false;
            }
        }
        return true;
    }

    /**
     *  Validate input data
     *  @param control Array the input Control
     *  @param rules Validation rules, The rules may be: required|number|decimal|alpha|letter
     *  @return: false if validation fail
     * */
    public boolean checkValidate(@NonNull RadioButton[] control, @NonNull String[] rules){
        for (int i=0; i<control.length;i++){
            if(!this.checkValidate(this.getValueFromControl(control[i]),rules)){
                return false;
            }
        }
        return true;
    }

    /**
     *  Validate input data
     *  @param control the input Control
     *  @param rules  Validation rules, The rules may be: required|number|decimal|alpha|letter
     *  @return: false if validation fail
     * */
    public boolean checkValidate(@NonNull RadioButton control,@NonNull String[] rules){
        return this.checkValidate(this.getValueFromControl(control),rules);
    }

    /**
     *  Validate input data
     *  @param control Array the input Control
     *  @param rules  Validation rules, equal rule to all input, these rules must be separated by bars "|", The rules may be: required|number|decimal|alpha|letter
     *  @return: false if validation fail
     * */
    public boolean checkValidate(@NonNull TextView[] control, @NonNull String rules){
        for (int i=0; i<control.length;i++){
            if(!this.checkValidate(this.getValueFromControl(control[i]),rules.split("|"))){
                return false;
            }
        }
        return true;
    }

    /**
     *  Validate input data
     *  @param control Array the input Control
     *  @param rules Array[n][1] Validation rules, these rules must be separated by bars "|", The rules may be: required|number|decimal|alpha|letter
     *  @return: false if validation fail
     * */
    public boolean checkValidate(@NonNull TextView[] control, @NonNull String[][] rules){
        for (int i=0; i<control.length;i++){
            if(!this.checkValidate(this.getValueFromControl(control[i]),rules[i][0].split("|"))){
                return false;
            }
        }
        return true;
    }

    /**
     *  Validate input data
     *  @param control Array the input Control
     *  @param rules Validation rules, The rules may be: required|number|decimal|alpha|letter
     *  @return: false if validation fail
     * */
    public boolean checkValidate(@NonNull TextView[] control, @NonNull String[] rules){
        for (int i=0; i<control.length;i++){
            if(!this.checkValidate(this.getValueFromControl(control[i]),rules)){
                return false;
            }
        }
        return true;
    }

    /**
     *  Validate input data
     *  @param control input Control
     *  @param rules Validation rules, these rules must be separated by bars "|", The rules may be: required|number|decimal|alpha|letter
     *  @return: false if validation fail
     * */
    public boolean checkValidate(@NonNull TextView control, @NonNull String rules){
        return this.checkValidate(this.getValueFromControl(control),rules.split("|"));
    }

    /**
     *  Validate input data
     *  @param control input Control
     *  @param rules Validation rules, The rules may be: required|number|decimal|alpha|letter
     *  @return: false if validation fail
     * */
    public boolean checkValidate(@NonNull TextView control,@NonNull String[] rules){
        return this.checkValidate(this.getValueFromControl(control),rules);
    }

    /**
     *  Validate input data
     *  @param values Array Input values
     *  @param rules Validation rules, The rules may be: required|number|decimal|alpha|letter
     *  @return: false if validation fail
     * */
    public boolean checkValidate(@NonNull String[] values,@NonNull String[] rules) {
        for (int i=0;i<values.length;i++){
            if (!this.checkValidate(values[i],rules)){
                return false;
            }
        }
        return true;
    }

    /**
     *  Validate input data
     *  @param values Array Input values
     *  @param rules Validation rules, these rules must be separated by bars "|", The rules may be: required|number|decimal|alpha|letter
     *  @return: false if validation fail
     * */
    public boolean checkValidate(@NonNull String[] values,@NonNull String[][] rules) {
        for (int i=0;i<values.length;i++){
            if (!this.checkValidate(values[i],rules[i][0].split("|"))){
                return false;
            }
        }
        return true;
    }

    /**
     *  Validate input data
     *  @param value Input value
     *  @param rules Validation rules, The rules may be: required|number|decimal|alpha|letter
     *  @return: false if validation fail
     * */
    public boolean checkValidate(@NonNull String value,@NonNull String[] rules) {
        for (int i=0;i<rules.length;i++){
            if (!this.checkValidate(value,rules[i])){
                return false;
            }
        }
        return true;
    }

    /**
     *  Validate input data
     *  @param control Array Input Control
     *  @param rules Validation rules, equal rule to all inputs, these rules must be separated by bars "|", The rules may be: required|number|decimal|alpha|letter
     *  @param msg Error msg, equal msg to all inputs, these msg must be separated by bars "|", the error message should be the same order as the validation rules
     *  @return: the error msg, string empty if success validation
     * */
    public String checkValidate(@NonNull RadioButton[] control,@NonNull String rules,String msg){
        String res="";
        for (int i=0;i<control.length;i++){
            res=res.concat("\n").concat(this.checkValidate(this.getValueFromControl(control[i]),rules.split("|"),msg.split("|")));
        }
        return res;
    }

    /**
     *  Validate input data
     *  @param control Array Input Control
     *  @param rules Array[n][0] Validation rules, these rules must be separated by bars "|", The rules may be: required|number|decimal|alpha|letter
     *  @param msg Array[n][0] Error msg, these msg must be separated by bars "|", the error message should be the same order as the validation rules
     *  @return: the error msg, string empty if success validation
     * */
    public String checkValidate(@NonNull RadioButton[] control,@NonNull String[][] rules,String[][] msg){
        String res="";
        for (int i=0;i<control.length;i++){
            res=res.concat("\n").concat(this.checkValidate(this.getValueFromControl(control[i]),rules[i][0].split("|"),msg[i][0].split("|")));
        }
        return res;
    }

    /**
     *  Validate input data
     *  @param control Array Input Control
     *  @param rules Validation rules, equal rule to all inputs, The rules may be: required|number|decimal|alpha|letter
     *  @param msg Error msg, equal msg to all inputs, the error message should be the same order as the validation rules
     *  @return: the error msg, string empty if success validation
     * */
    public String checkValidate(@NonNull RadioButton[] control,@NonNull String[] rules,String[] msg){
        String res="";
        for (int i=0;i<control.length;i++){
            res=res.concat("\n").concat(this.checkValidate(this.getValueFromControl(control[i]),rules,msg));
        }
        return res;
    }

    /**
     *  Validate input data
     *  @param control Input Control
     *  @param rules Validation rules, these rules must be separated by bars "|", The rules may be: required|number|decimal|alpha|letter
     *  @param msg Error msg, these msg must be separated by bars "|", the error message should be the same order as the validation rules
     *  @return: the error msg, string empty if success validation
     * */
    public String checkValidate(@NonNull RadioButton control,@NonNull String rules,String msg){
        return this.checkValidate(this.getValueFromControl(control),rules.split("|"),msg.split("|"));
    }

    /**
     *  Validate input data
     *  @param control Input Control
     *  @param rules Validation rules, The rules may be: required|number|decimal|alpha|letter
     *  @param msg Error msg, the error message should be the same order as the validation rules
     *  @return: the error msg, string empty if success validation
     * */
    public String checkValidate(@NonNull RadioButton control,@NonNull String[] rules,@NonNull String[] msg){
        return this.checkValidate(this.getValueFromControl(control),rules,msg);
    }


    /**
     *  Validate input data
     *  @param control Array Input Control
     *  @param rules Validation rules, equal rule to all inputs, these rules must be separated by bars "|", The rules may be: required|number|decimal|alpha|letter
     *  @param msg Error msg, equal msg to all inputs, these msg must be separated by bars "|", the error message should be the same order as the validation rules
     *  @return: the error msg, string empty if success validation
     * */
    public String checkValidate(@NonNull TextView[] control,@NonNull String rules,String msg){
        String res="";
        for (int i=0;i<control.length;i++){
            res=res.concat("\n").concat(this.checkValidate(this.getValueFromControl(control[i]),rules.split("|"),msg.split("|")));
        }
        return res;
    }

    /**
     *  Validate input data
     *  @param control Array Input Control
     *  @param rules Array[n][0] Validation rules, these rules must be separated by bars "|", The rules may be: required|number|decimal|alpha|letter
     *  @param msg Array[n][0] Error msg, these msg must be separated by bars "|", the error message should be the same order as the validation rules
     *  @return: the error msg, string empty if success validation
     * */
    public String checkValidate(@NonNull TextView[] control,@NonNull String[][] rules,String[][] msg){
        String res="";
        for (int i=0;i<control.length;i++){
            res=res.concat("\n").concat(this.checkValidate(this.getValueFromControl(control[i]),rules[i][0].split("|"),msg[i][0].split("|")));
        }
        return res;
    }

    /**
     *  Validate input data
     *  @param control Array Input Control
     *  @param rules Validation rules, equal rule to all inputs, The rules may be: required|number|decimal|alpha|letter
     *  @param msg Error msg, equal msg to all inputs, the error message should be the same order as the validation rules
     *  @return: the error msg, string empty if success validation
     * */
    public String checkValidate(@NonNull TextView[] control,@NonNull String[] rules,String[] msg){
        String res="";
        for (int i=0;i<control.length;i++){
            res=res.concat("\n").concat(this.checkValidate(this.getValueFromControl(control[i]),rules,msg));
        }
        return res;
    }

    /**
     *  Validate input data
     *  @param control Input Control
     *  @param rules Validation rules, these rules must be separated by bars "|", The rules may be: required|number|decimal|alpha|letter
     *  @param msg Error msg, these msg must be separated by bars "|", the error message should be the same order as the validation rules
     *  @return: the error msg, string empty if success validation
     * */
    public String checkValidate(@NonNull TextView control,@NonNull String rules,String msg){
        return this.checkValidate(control,rules.split("|"),msg.split("|"));
    }

    /**
     *  Validate input data
     *  @param control Input Control
     *  @param rules Validation rules, The rules may be: required|number|decimal|alpha|letter
     *  @param msg Error msg, the error message should be the same order as the validation rules
     *  @return: the error msg, string empty if success validation
     * */
    public String checkValidate(@NonNull TextView control,@NonNull String[] rules,@NonNull String[] msg){
        return this.checkValidate(this.getValueFromControl(control),rules,msg);
    }

    /**
     *  Validate input data
     *  @param values Array Input Values
     *  @param rules Validation rules, equal rule to all inputs, The rules may be: required|number|decimal|alpha|letter
     *  @param msg Error msg, equal msg to all inputs, the error message should be the same order as the validation rules
     *  @return: the error msg, string empty if success validation
     * */
    public String checkValidate(@NonNull String[] values,@NonNull String[] rules,@NonNull String[] msg) {
        String res="";
        for (int i=0;i<values.length;i++){
            res=res.concat("\n").concat(this.checkValidate(values[i],rules,msg));
        }
        return res;
    }

    /**
     *  Validate input data
     *  @param values Array Input Values
     *  @param rules Validation rules, these rules must be separated by bars "|", The rules may be: required|number|decimal|alpha|letter
     *  @param msg Error msg, these msg must be separated by bars "|", the error message should be the same order as the validation rules
     *  @return: the error msg, string empty if success validation
     * */
    public String checkValidate(@NonNull String[] values,@NonNull String[][] rules,@NonNull String[][] msg) {
        String res="";
        for (int i=0;i<values.length;i++){
            res=res.concat("\n").concat(this.checkValidate(values[i],rules[i][0].split("|"),msg[i][0].split("|")));
        }
        return res;
    }

    /**
     *  Validate input data
     *  @param value Input Value
     *  @param rules Validation rules, The rules may be: required|number|decimal|alpha|letter
     *  @param msg Error msg, the error message should be the same order as the validation rules
     *  @return: the error msg, string empty if success validation
     * */
    public String checkValidate(@NonNull String value,@NonNull String[] rules,@NonNull String[] msg) {
        String res="";
        for (int i=0;i<rules.length;i++){
            if (!this.checkValidate(value,rules[i])){
                res=res.concat("\n").concat(msg[i]);
            }
        }
        return res;
    }

    /**
     *  Validate input data
     *  @param value Input Values
     *  @param rule Validation rule, The rule may be: required|number|decimal|alpha|letter
     *  @return: false if validation fail or rule no exist
     * */
    public boolean checkValidate(@NonNull String value,@NonNull String rule){
        switch (rule){
            case "required": return !value.equals("");
            case "number": return this.isInt(value);
            case "decimal": return this.isFloat(value);
            case "alpha": return !this.isFloat(value);
            case "letter": return !this.isFloat(value);
        }
        return false;
    }

    /**
     *  Validate if data is integer number
     *  @param data string to validate
     *  @return: false if cannot parse to integer number
     * */
    private boolean isInt(@NonNull String data){
        try {
            Integer.parseInt(data);
            return true;
        }catch (NumberFormatException e){
            return false;
        }
    }

    /**
     *  Validate if data is number with decimal, or float
     *  @param data string to validate
     *  @return: false if cannot parse to float number
     * */
    private boolean isFloat(@NonNull String data){
        try {
            Float.parseFloat(data);
            return true;
        }catch (NumberFormatException e){
            return false;
        }
    }

    /**
     *  get value the textView control
     *  @param control to get value
     *  @return: the value
     * */
    @NonNull
    private String getValueFromControl(TextView control){
        return control.getText().toString();
    }

    /**
     *  get value the radioButton control
     *  @param control to get value
     *  @return: the value
     * */
    @NonNull
    private String getValueFromControl(RadioButton control){
        return control.getText().toString();
    }
}

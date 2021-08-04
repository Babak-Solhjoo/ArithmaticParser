import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.Math;
import java.util.Scanner;

public class Arithmatic_Prcessor {

    public static void main(String[] args)
            throws IOException
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String phrase = reader.readLine();
        phrase = phrase.replace(" ", "");

        String paranthesis_phrase;
        String simplified_phrase;

        int first_close_paranthesis_index;
        int last_open_paranthesis_index;
        int counter=0;
        boolean parathesis_exists=true;

        while(parathesis_exists)
        {
            first_close_paranthesis_index = phrase.indexOf(")");
            if(first_close_paranthesis_index>0) {
                paranthesis_phrase = phrase.substring(0, first_close_paranthesis_index);
                last_open_paranthesis_index = paranthesis_phrase.lastIndexOf("(");
                paranthesis_phrase = paranthesis_phrase.substring(last_open_paranthesis_index + 1);
                simplified_phrase = Multiplier_Simplifier(paranthesis_phrase);
                simplified_phrase = Sum_Simplifier(simplified_phrase);
                phrase = phrase.substring(0, last_open_paranthesis_index) + simplified_phrase + phrase.substring(first_close_paranthesis_index + 1);
            }
            else
            {
                simplified_phrase = Multiplier_Simplifier(phrase);
                simplified_phrase = Sum_Simplifier(simplified_phrase);
                System.out.println(simplified_phrase);
                parathesis_exists=false;
            }
        }
    }

    public static String Multiplier_Simplifier(String phrase)
    {

        int first_multiplier_index;
        int first_division_index;

        int previous_operator_index_mul = 0;
        int previous_operator_index_div = 0;
        int previous_operator_index_sum = 0;
        int previous_operator_index_sub = 0;
        int previous_operator_index = 0;
        int next_operator_index_mul = 0;
        int next_operator_index_div = 0;
        int next_operator_index_sum = 0;
        int next_operator_index_sub = 0;
        int next_operator_index = 0;
        int phrase_len = 0;

        int min_index_mult_div = 1;
        int operator_code = -1;
        double pre_oprator = 0;
        double post_operator = 0;
        double result = 0;

        boolean operator_exists_flag = true;


        //Multiplication Processor
        while (operator_exists_flag) {

            //Calculating inside of a paranthesis
            //Multiplication-Division
            first_multiplier_index = phrase.indexOf("*");
            first_division_index = phrase.indexOf("/");

            if ((first_multiplier_index > -1) && (first_division_index > -1) && (first_multiplier_index < first_division_index)) {
                min_index_mult_div = first_multiplier_index;
                operator_code = 1;
            }

            if ((first_multiplier_index > -1) && (first_division_index > -1) && (first_multiplier_index > first_division_index)) {
                min_index_mult_div = first_division_index;
                operator_code = 2;
            }

            if ((first_multiplier_index > -1) && (first_division_index == -1)) {
                min_index_mult_div = first_multiplier_index;
                operator_code = 1;
            }

            if ((first_multiplier_index == -1) && (first_division_index > -1)) {
                min_index_mult_div = first_division_index;
                operator_code = 2;
            }

            if ((first_multiplier_index == -1) && (first_division_index == -1)) {
                min_index_mult_div = -1;
            }

            if (min_index_mult_div > -1) {
                previous_operator_index_mul = phrase.substring(0, min_index_mult_div).lastIndexOf("*");
                previous_operator_index_div = phrase.substring(0, min_index_mult_div).lastIndexOf("/");
                previous_operator_index_sum = phrase.substring(0, min_index_mult_div).lastIndexOf("+");
                previous_operator_index_sub = phrase.substring(0, min_index_mult_div).lastIndexOf("-");
                previous_operator_index = Math.max(previous_operator_index_mul, previous_operator_index_div);
                previous_operator_index = Math.max(previous_operator_index, previous_operator_index_sum);
                previous_operator_index = Math.max(previous_operator_index, previous_operator_index_sub);

                next_operator_index_mul = phrase.substring(min_index_mult_div + 1).indexOf("*");
                next_operator_index_div = phrase.substring(min_index_mult_div + 1).indexOf("/");
                next_operator_index_sum = phrase.substring(min_index_mult_div + 1).indexOf("+");
                next_operator_index_sub = phrase.substring(min_index_mult_div + 1).indexOf("-");

                phrase_len = phrase.length();

                if (next_operator_index_mul == -1) next_operator_index_mul = phrase_len + 2;
                if (next_operator_index_div == -1) next_operator_index_div = phrase_len + 2;
                if (next_operator_index_sum == -1) next_operator_index_sum = phrase_len + 2;
                if (next_operator_index_sub == -1) next_operator_index_sub = phrase_len + 2;

                next_operator_index = Math.min(next_operator_index_mul, next_operator_index_div);
                next_operator_index = Math.min(next_operator_index, next_operator_index_sum);
                next_operator_index = Math.min(next_operator_index, next_operator_index_sub);
                next_operator_index = Math.min(next_operator_index, phrase.substring(min_index_mult_div + 1).length());

                pre_oprator = Double.parseDouble(phrase.substring(previous_operator_index + 1, min_index_mult_div));
                //System.out.println(pre_oprator);

                post_operator = Double.parseDouble(phrase.substring(min_index_mult_div + 1, min_index_mult_div + 1 + next_operator_index));
                //System.out.println(post_operator);

                switch (operator_code) {
                    case 1:
                        result = pre_oprator * post_operator;
                        break;

                    case 2:
                        result = pre_oprator / post_operator;
                        break;
                }

                phrase = phrase.substring(0, previous_operator_index + 1) + String.valueOf(result) + phrase.substring(min_index_mult_div + 1 + next_operator_index);
            }

            if (min_index_mult_div == -1) operator_exists_flag = false;
            //System.out.println(phrase);
        }
        return phrase;
    }

    public static String Sum_Simplifier(String phrase)
    {
        int sign = 0;
        int previous_operator_index_sum = 0;
        int previous_operator_index_sub = 0;
        int previous_operator_index = 0;
        int next_operator_index_sum = 0;
        int next_operator_index_sub = 0;
        int next_operator_index = 0;
        int phrase_len = 0;

        int first_sum_index = 0;
        int first_sub_index = 0;
        int min_index_sum_sub = 1;
        int operator_code = -1;
        double pre_oprator = 0;
        double post_operator = 0;
        double result = 0;

        boolean operator_exists_flag = true;
        //Summation Processor
        while(operator_exists_flag)
        {
            //Calculating inside of a paranthesis
            //Multiplication-Division
            sign=0;
            first_sum_index = phrase.indexOf("+");
            first_sub_index = phrase.indexOf("-");

            if(first_sum_index==0)
            {
                phrase=phrase.substring(1);
                first_sum_index=phrase.indexOf("+");
                first_sub_index=phrase.indexOf("-");
                sign=1;
            }


            if(first_sub_index==0)
            {
                phrase=phrase.substring(1);
                first_sub_index=phrase.indexOf("-");
                first_sum_index=phrase.indexOf("+");
                sign=-1;
            }

            if((first_sum_index>0)&&(first_sub_index>0)&&(first_sum_index<first_sub_index))
            {
                min_index_sum_sub = first_sum_index;
                operator_code = 3;
                if(sign==-1)operator_code = 5;
            }

            if((first_sum_index>0)&&(first_sub_index>0)&&(first_sum_index>first_sub_index))
            {
                min_index_sum_sub = first_sub_index;
                operator_code = 4;
                if(sign==-1)operator_code = 6;
            }

            if((first_sum_index>0)&&(first_sub_index<=0))
            {
                min_index_sum_sub = first_sum_index;
                operator_code = 3;
                if(sign==-1)operator_code = 5;
            }

            if((first_sum_index<=0)&&(first_sub_index>0))
            {
                min_index_sum_sub = first_sub_index;
                operator_code = 4;
                if(sign==-1)operator_code = 6;
            }

            if((first_sum_index<=0)&&(first_sub_index<=0))
            {
                min_index_sum_sub=-1;
            }

            if(min_index_sum_sub>-1)
            {

                previous_operator_index_sum = phrase.substring(0, min_index_sum_sub).lastIndexOf("+");
                previous_operator_index_sub = phrase.substring(0, min_index_sum_sub).lastIndexOf("-");
                previous_operator_index = Math.max(previous_operator_index_sum, previous_operator_index_sub);

                next_operator_index_sum = phrase.substring(min_index_sum_sub+1).indexOf("+");
                next_operator_index_sub = phrase.substring(min_index_sum_sub+1).indexOf("-");

                phrase_len = phrase.length();

                if(next_operator_index_sum==-1)next_operator_index_sum=phrase_len+2;
                if(next_operator_index_sub==-1)next_operator_index_sub=phrase_len+2;

                next_operator_index = Math.min(next_operator_index_sum, next_operator_index_sub);
                next_operator_index = Math.min(next_operator_index, phrase.substring(min_index_sum_sub+1).length());

                pre_oprator = Double.parseDouble(phrase.substring(previous_operator_index + 1, min_index_sum_sub));
                //System.out.println(pre_oprator);

                post_operator = Double.parseDouble(phrase.substring(min_index_sum_sub+1, min_index_sum_sub + 1 + next_operator_index));
                //System.out.println(post_operator);

                switch (operator_code)
                {
                    case 3:
                        result = pre_oprator + post_operator;
                        break;

                    case 4:
                        result = pre_oprator - post_operator;
                        break;

                    case 5:
                        result = -pre_oprator + post_operator;
                        break;

                    case 6:
                        result = -pre_oprator-post_operator;
                        break;
                }

                phrase=phrase.substring(0, previous_operator_index + 1)+String.valueOf(result)+phrase.substring(min_index_sum_sub+ 1 + next_operator_index);

            }


            //Sum-Sub
            if(min_index_sum_sub==-1)
            {
                operator_exists_flag=false;
                if(sign==-1)phrase="-"+phrase;
            }

            //System.out.println(phrase);
        }
//2+ (7*2)*53-(32/2)
// -2-3+4*5+8/2-10-3-8-15

        //System.out.println(phrase);
        return phrase;
    }

}

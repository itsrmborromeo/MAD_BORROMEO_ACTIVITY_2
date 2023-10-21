package com.activity.mad_borromeo_activity2;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    private static String num;
    TextView primaryscrn, secondaryscrn;
    Boolean root=false;
    Double rootnum=0.0,basenum=0.0;
    Button bt0,bt1,bt2,bt3,bt4,bt5,bt6,bt7,bt8,bt9,bt00,add,sub,multiply,divide,modulo,
            parenthesis,parenthesis2,equal,dot,exponents,log,roots,allclear,delete,reciprocal,sqroot;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        primaryscrn = findViewById(R.id.primary);
        secondaryscrn =findViewById(R.id.secondary);

        bt00 = findViewById(R.id.doublezero);
        bt0 = findViewById(R.id.num0);
        bt1=findViewById(R.id.num1);
        bt2=findViewById(R.id.num2);
        bt3=findViewById(R.id.num3);
        bt4=findViewById(R.id.num4);
        bt5=findViewById(R.id.num5);
        bt6=findViewById(R.id.num6);
        bt7=findViewById(R.id.num7);
        bt8=findViewById(R.id.num8);
        bt9=findViewById(R.id.num9);

        add=findViewById(R.id.add);
        sub=findViewById(R.id.subtract);
        multiply=findViewById(R.id.multiply);
        divide=findViewById(R.id.divide);
        modulo=findViewById(R.id.modulo);

        exponents=findViewById(R.id.exponent);
        log=findViewById(R.id.log);
        roots=findViewById(R.id.root_of_n);
        sqroot = findViewById(R.id.square_root);
        reciprocal=findViewById(R.id.reciprocal);
        allclear=findViewById(R.id.all_clear);
        delete=findViewById(R.id.delete);
        parenthesis=findViewById(R.id.open_parenthesis);
        parenthesis2=findViewById(R.id.close_parenthesis);
        equal=findViewById(R.id.equals);
        dot=findViewById(R.id.dot);


        bt00.setOnClickListener(view -> primaryscrn.setText(primaryscrn.getText()+"00"));
        bt0.setOnClickListener(view -> primaryscrn.setText(primaryscrn.getText()+"0"));
        bt1.setOnClickListener(view -> primaryscrn.setText(primaryscrn.getText()+"1"));
        bt2.setOnClickListener(view -> primaryscrn.setText(primaryscrn.getText()+"2"));
        bt3.setOnClickListener(view -> primaryscrn.setText(primaryscrn.getText()+"3"));
        bt4.setOnClickListener(view -> primaryscrn.setText(primaryscrn.getText()+"4"));
        bt5.setOnClickListener(view -> primaryscrn.setText(primaryscrn.getText()+"5"));
        bt6.setOnClickListener(view -> primaryscrn.setText(primaryscrn.getText()+"6"));
        bt7.setOnClickListener(view -> primaryscrn.setText(primaryscrn.getText()+"7"));
        bt8.setOnClickListener(view -> primaryscrn.setText(primaryscrn.getText()+"8"));
        bt9.setOnClickListener(view -> primaryscrn.setText(primaryscrn.getText()+"9"));
        add.setOnClickListener(view -> primaryscrn.setText(primaryscrn.getText()+"+"));
        sub.setOnClickListener(view -> primaryscrn.setText(primaryscrn.getText()+"-"));
        multiply.setOnClickListener(view -> primaryscrn.setText(primaryscrn.getText()+"*"));
        divide.setOnClickListener(view -> primaryscrn.setText(primaryscrn.getText()+"÷"));
        modulo.setOnClickListener(view -> primaryscrn.setText(primaryscrn.getText()+"%"));
        exponents.setOnClickListener(view -> primaryscrn.setText(primaryscrn.getText()+"^"));
        parenthesis.setOnClickListener(view -> primaryscrn.setText(primaryscrn.getText()+"("));
        parenthesis2.setOnClickListener(view -> primaryscrn.setText(primaryscrn.getText()+")"));
        log.setOnClickListener(view -> primaryscrn.setText(primaryscrn.getText()+"log"));
        reciprocal.setOnClickListener(view -> primaryscrn.setText(primaryscrn.getText()+"^"+"(-1)"));
        dot.setOnClickListener(view -> primaryscrn.setText(primaryscrn.getText()+"."));

        roots.setOnClickListener(view -> {
            root=true;
            String val = primaryscrn.getText().toString();
            rootnum = (Double.parseDouble(val));
            primaryscrn.setText(primaryscrn.getText()+"√");
        });

        sqroot.setOnClickListener(view -> {
            String num = primaryscrn.getText().toString();
            double rnum = Math.round(Math.sqrt(Double.parseDouble(num)));
            secondaryscrn.setText(String.valueOf(rnum));
        });

        delete.setOnClickListener(view -> {
            String val= primaryscrn.getText().toString();
            val=val.substring(0,val.length()-1);
            primaryscrn.setText(val);
        });
        allclear.setOnClickListener(view -> {
            primaryscrn.setText("");
            secondaryscrn.setText("");
        });

        equal.setOnClickListener(view -> {
            if(root){
                num = primaryscrn.getText().toString();
                String temporary = findnum(num);
                basenum=(Double.parseDouble(temporary));
                double result=Math.pow(basenum,1.0/rootnum);
                secondaryscrn.setText(String.valueOf(result));
            }else {
                String val =primaryscrn.getText().toString();
                String replacedstr = val.replace('÷','/');
                double result = eval(replacedstr);
                secondaryscrn.setText(String.valueOf(result));
            }
        });
    }

    public static String findnum(String str){
        String eq = String.valueOf(num);
        String pattern = ".*√\\s*([\\d.]+)";
        Pattern regex = Pattern.compile(pattern);
        Matcher matcher = regex.matcher(eq);

        if (matcher.find()) {
            String numberStr = matcher.group(1);
            assert numberStr != null;
            double number = Double.parseDouble(numberStr);
            str = String.valueOf(number);
        }
        return str;
    }

    public static double eval(final String str) {
        return new Object() {
            int pos = -1, ch;

            void nextChar() {
                ch = (++pos < str.length()) ? str.charAt(pos) : -1;
            }

            boolean eat(int charToEat) {
                while (ch == ' ') nextChar();
                if (ch == charToEat) {
                    nextChar();
                    return true;
                }
                return false;
            }

            double parse() {
                nextChar();
                double x = parseExpression();
                if (pos < str.length()) throw new RuntimeException("Unexpected: " + (char)ch);
                return x;
            }

            double parseExpression() {
                double x = parseTerm();
                for (;;) {
                    if      (eat('+')) x += parseTerm();
                    else if (eat('-')) x -= parseTerm();
                    else return x;
                }
            }

            double parseTerm() {
                double x = parseFactor();
                for (;;) {
                    if      (eat('*')) x *= parseFactor();
                    else if (eat('/')) x /= parseFactor();
                    else if (eat('%')) x %=parseFactor();
                    else return x;
                }
            }

            double parseFactor() {
                if (eat('+')) return parseFactor();
                if (eat('-')) return -parseFactor();

                double x;
                int startPos = this.pos;
                if (eat('(')) { // parentheses
                    x = parseExpression();
                    eat(')');
                } else if ((ch >= '0' && ch <= '9') || ch == '.') { // numbers
                    while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
                    x = Double.parseDouble(str.substring(startPos, this.pos));
                } else if (ch >= 'a' && ch <= 'z') { // functions
                    while (ch >= 'a' && ch <= 'z') nextChar();
                    String func = str.substring(startPos, this.pos);
                    x = parseFactor();
                    if (func.equals("sqrt")) x = Math.sqrt(x);
                    else if (func.equals("log")) x = Math.log10(x);
                    else throw new RuntimeException("Unknown function: " + func);
                } else {
                    throw new RuntimeException("Unexpected: " + (char)ch);
                }

                if (eat('^')) x = Math.pow(x, parseFactor());
                return x;
            }
        }.parse();
    }

}
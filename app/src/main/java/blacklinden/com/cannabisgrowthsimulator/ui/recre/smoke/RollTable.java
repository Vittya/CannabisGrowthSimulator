package blacklinden.com.cannabisgrowthsimulator.ui.recre.smoke;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Toast;


import androidx.annotation.Nullable;


import java.util.HashMap;
import java.util.Objects;
import java.util.Stack;


import blacklinden.com.cannabisgrowthsimulator.R;
import blacklinden.com.cannabisgrowthsimulator.ui.recre.drag.DragElement;

public class RollTable extends View implements DragElement {

    private Paint paint;
    private Drawable drawable;
    private Stack<JointElement> items;
    private Paper paper;
    private Rect cp,csiga,dohany,cucc;
    private Stack<JointElement> backupItems;
    private StringBuilder controlStr;
    private BackUpListener listener;


    public RollTable(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setColor(Color.BLUE);
        drawable =( context.getResources().getDrawable(R.drawable.als_box,null));
        items = new Stack<>();
        backupItems = new Stack<>();
        cp = new Rect(0,getHeight()/3,getWidth(),getHeight());
        controlStr = new StringBuilder();


    }

    @Override
    protected void onDraw(Canvas c) {
        super.onDraw(c);
        //drawable.setBounds(0,0,getWidth(),getHeight());
       // drawable.draw(c);
        //c.drawRect(0,0,getWidth(),getHeight(),paint);
        controller(c);

    }

    private void controller(Canvas c){

        if (items.isEmpty()) {
           drawHUD(c);
        } else {
            for(int i =0;i<items.size();i++) {

                drawToSlots(c,items.get(i),i);
            }
        }

    }

    private void drawHUD(Canvas c){
        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.FILL);
        c.drawRect(0,0,getWidth(),getHeight(),paint);
    }

    private void drawToSlots(Canvas c,JointElement item, int index){

        if(item instanceof Paper){

            if(index>0&&items.get(--index) instanceof Paper){
                   Rect boundsw = item.getRect();
                    boundsw.offsetTo(0, getHeight() / 6);
                    item.draw(boundsw, c);
            }else
                item.draw(null,c);

        }else if(item instanceof Bobi){

            item.draw(new Rect(30,getHeight()/4,getWidth()-30,getHeight()-getHeight()/4),c);
        }
    }

    public void loadStack(JointElement item){
        int freqLimit=0;
        if(item instanceof Paper)
            freqLimit=2;
        else if(item instanceof Bobi)
            freqLimit=3;
        int freq = occurrenceFreq(item);
        if((freq <freqLimit)) {

            stringControl(item);
            items.push(item);
            invalidate();
        }

        Toast.makeText(getContext(),"class: "+item.getClass()+" freq: "+freq,Toast.LENGTH_SHORT ).show();


    }

    private void stringControl(JointElement item){
        if(item instanceof Bobi)
            controlStr.append("bobi");
        else
            controlStr.append(item.getName());
        if(!(item instanceof Bobi))
            controlStr.append(item.getState());
    }

    public Stack<JointElement> validateCombo(){
        Stack<JointElement> joint = new Stack<>();
        String string = controlStr.toString();
        String match  = ComboControl.match(string);
       if(!match.equals("")) {
           Toast.makeText(getContext(), match, Toast.LENGTH_SHORT).show();
           joint.addAll(items);
           items.clear();
           controlStr.setLength(0);
           invalidate();
       }else Toast.makeText(getContext(), string, Toast.LENGTH_SHORT).show();
       return joint;
    }

    public void deleteItem(){
        if(!items.isEmpty()){
            backupItems.push(items.pop());
            if(listener!=null)
            listener.preview(getPreviewElement());
            controlStr.setLength(0);
            items.forEach(this::stringControl);
            invalidate();
        }
    }

    public void undoDeleteItem(){
        if(!backupItems.isEmpty()){
            items.push(backupItems.pop());
            if(listener!=null)
                listener.preview(getPreviewElement());
            controlStr.setLength(0);
            items.forEach(this::stringControl);
            invalidate();
        }

    }

    public void backupPreview(BackUpListener listener){

        if(listener!=null)
            this.listener=listener;

    }

    private JointElement getPreviewElement(){
        if(!backupItems.isEmpty())
            return backupItems.peek();
        else
            return null;
    }
//román függvény
    private int occurrenceFreq(JointElement item){
        return (int)items.stream().filter(i -> i.getClass().equals(item.getClass())).count();
    }

    public void clearStack(){
        items.clear();
        controlStr.setLength(0);
        backupItems.clear();
        invalidate();
    }


    @Override
    public void hiLite(boolean isLit) {
        Toast.makeText(getContext(),"isLit:"+isLit,Toast.LENGTH_SHORT).show();

    }

    static class ComboControl{
        static HashMap<String,String> combos = new HashMap<>();

        static{
            combos.put("Roach",""+R.drawable.ic_cigiszelveny1+"falsebobi");
        }


        static String match(String input){
            final String[] currentresult = {""};
            combos.forEach((k,v)->{
                if(input.equalsIgnoreCase(v))
                    currentresult[0]=k;

                else if(input.contains(v)){
                    //string hosszt vizsgál
                    if(currentresult[0].equals(""))
                        currentresult[0]=k;
                     else if(Objects.requireNonNull(combos.get(currentresult[0])).length()<v.length())
                         currentresult[0]=k;
                }

            });

            return currentresult[0];
        }
    }

    public interface BackUpListener{

       void preview(JointElement element);
    }


}

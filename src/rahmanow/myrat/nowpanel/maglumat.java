package rahmanow.myrat.nowpanel;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;


//import com.android.this.R;

/**
 * Created by Administrator on 21.11.2015.
 */
public class maglumat extends DialogFragment implements DialogInterface.OnClickListener {

    private View form = null;
    //private TextView inf;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        //View view = getActivity().getLayoutInflater().inflate(R.layout.about, null);


        //inf = (TextView) view.findViewById(R.id.t_maglumat);
        //inf.setText(Html.fromHtml("© 2015 Myrat Rahmanow\n<a href=\"https://cgcod.blogspot.com/\">Link</a>\n\n<b>Wersiýa:</b> 1.1\nIslendik teklipleri we ýalňyşlyklary \ntaslamacy@gmail.com salga iberip bilersiňiz."));
        //inf.setMovementMethod(LinkMovementMethod.getInstance());
        form = getActivity().getLayoutInflater()
                .inflate(R.layout.about, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), 3);



        return (builder.setTitle(R.string.i).setView(form)
                .setPositiveButton(android.R.string.ok, this).create());
    }
    @Override
    public void onClick(DialogInterface dialogInterface, int i) {

    }
}

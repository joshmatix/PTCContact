package com.sharperture.remotecsv;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {

    private GridView gridView;
    private ArrayList<String> items = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
/*        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/
        ptcContactArrayAdapterClass ptcContactArrayAdapter =
                new ptcContactArrayAdapterClass(this, PTCContacts);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        gridView = (GridView) findViewById(R.id.gridView1);

        gridView.setOnItemClickListener( new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
//        gridView.setAdapter(new GridAdapter(items));
        gridView.setAdapter( ptcContactArrayAdapter);

    }
    static class PTCContact {
        String ptc_name;
        String ptc_phone1;
        String ptc_phone2;
        String ptc_email;
        String ptc_address;

        public PTCContact(String ptc_name, String ptc_phone1, String ptc_phone2, String ptc_email, String ptc_address) {
            this.ptc_name = ptc_name;
            this.ptc_phone1 = ptc_phone1;
            this.ptc_phone2 = ptc_phone2;
            this.ptc_email = ptc_email;
            this.ptc_address = ptc_address;
        }
    }
    static class ViewHolder{
        TextView ptc_name;
        TextView ptc_phone1;
        TextView ptc_phone2;
        TextView ptc_email;
        TextView ptc_address;
    }
    PTCContact[] PTCContacts = {
            new PTCContact("tom1", "123", "456", "t1@yahoo.com", "123 Main"),
            new PTCContact("tom2", "234", "567", "t2@yahoo.com", "234 Main")
    };
    //https://code.tutsplus.com/tutorials/android-from-scratch-understanding-adapters-and-adapter-views--cms-26646

    class ptcContactArrayAdapterClass extends ArrayAdapter<PTCContact> {
        public ptcContactArrayAdapterClass(Context context, PTCContact[] PTCContacts) {
            super(context, 0, PTCContacts);
        }
        @Override
        public View getView(int position,
                            View convertView,
                            ViewGroup parent) {
            PTCContact currentPTCContact = PTCContacts[position];
            // Inflate only once
            if(convertView == null) {
                ViewHolder viewHolder = new ViewHolder();
                convertView = getLayoutInflater()
                        .inflate(R.layout.custom_item, null, false);

                viewHolder.ptc_name =
                        (TextView)convertView.findViewById(R.id.ptc_name);
                viewHolder.ptc_phone1 =
                        (TextView)convertView.findViewById(R.id.ptc_phone1);
                viewHolder.ptc_phone2 =
                        (TextView)convertView.findViewById(R.id.ptc_phone2);
                viewHolder.ptc_email =
                        (TextView)convertView.findViewById(R.id.ptc_email);
                viewHolder.ptc_address =
                        (TextView)convertView.findViewById(R.id.ptc_address);

                // Store results of findViewById
                convertView.setTag(viewHolder);
            }


            TextView ptcName =
                    ((ViewHolder)convertView.getTag()).ptc_name;
            TextView ptcPhone1 =
                    ((ViewHolder)convertView.getTag()).ptc_phone1;
            TextView ptcPhone2 =
                    ((ViewHolder)convertView.getTag()).ptc_phone2;
            TextView ptcEmail =
                    ((ViewHolder)convertView.getTag()).ptc_email;
            TextView ptcAddress =
                    ((ViewHolder)convertView.getTag()).ptc_address;

            ptcName.setText(currentPTCContact.ptc_name);
            ptcPhone1.setText(currentPTCContact.ptc_phone1);
            ptcPhone2.setText(currentPTCContact.ptc_phone2);
            ptcEmail.setText(currentPTCContact.ptc_email);
            ptcAddress.setText(currentPTCContact.ptc_address);


            return  convertView;
        }
    }

/*    //////////////////////
    // Assume it's known
    private static final int ROW_ITEMS = 4;
    private static final class GridAdapter extends BaseAdapter {

        final ArrayList<String> mItems;
        final int mCount;

        /**
         * Default constructor
         * @param items to fill data to
         * /
        private GridAdapter(final ArrayList<String> items) {

            mCount = items.size() * ROW_ITEMS;
            mItems = new ArrayList<String>(mCount);

            // for small size of items it's ok to do it here, sync way
            for (String item : items) {
                // get separate string parts, divided by ,
                final String[] parts = item.split("/t");

                // remove spaces from parts
                for (String part : parts) {
                    part.replace(" ", "");
                    mItems.add(part);
                }
            }
        }

        @Override
        public int getCount() {
            return mCount;
        }

        @Override
        public Object getItem(final int position) {
            return mItems.get(position);
        }

        @Override
        public long getItemId(final int position) {
            return position;
        }

        @Override
        public View getView(final int position, final View convertView, final ViewGroup parent) {

            View view = convertView;

            if (view == null) {
                view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
            }

            final TextView text = (TextView) view.findViewById(android.R.id.text1);

            //tom   if(position<6)
            //       text.setText(mItems.get(position));

            return view;
        }
    }
    //////////////////////
*/
}

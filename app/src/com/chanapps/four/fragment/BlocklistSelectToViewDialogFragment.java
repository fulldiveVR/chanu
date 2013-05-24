package com.chanapps.four.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import com.chanapps.four.activity.R;
import com.chanapps.four.data.ChanBlocklist;

import java.util.*;

/**
* Created with IntelliJ IDEA.
* User: arley
* Date: 12/14/12
* Time: 12:44 PM
* To change this template use File | Settings | File Templates.
*/
public class BlocklistSelectToViewDialogFragment extends ListDialogFragment {

    public static final String TAG = BlocklistViewDialogFragment.class.getSimpleName();

    private Map<ChanBlocklist.BlockType, Set<String>> blocklist;
    private String[] displayBlockTypes;

    public BlocklistSelectToViewDialogFragment(SettingsFragment fragment)
    {
        this.blocklist = ChanBlocklist.getBlocklist(fragment.getActivity().getApplicationContext());
        List<ChanBlocklist.BlockType> blockTypes = new ArrayList<ChanBlocklist.BlockType>();
        for (ChanBlocklist.BlockType blockType : ChanBlocklist.BlockType.values())
            if (blocklist.containsKey(blockType) && blocklist.get(blockType).size() > 0)
                blockTypes.add(blockType);
        this.displayBlockTypes = new String[blockTypes.size()];
        int i = 0;
        for (ChanBlocklist.BlockType blockType : blockTypes)
            this.displayBlockTypes[i++] = blockType.displayString();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Log.e(TAG, "blocktypes = " + Arrays.toString(displayBlockTypes));
        return createListDialog(R.string.blocklist_select_view_type,
                R.string.dialog_blocklist, R.string.blocklist_empty,
                displayBlockTypes, new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String s = ((TextView)view).getText().toString();
                ChanBlocklist.BlockType blockType = null;
                for (ChanBlocklist.BlockType b : ChanBlocklist.BlockType.values())
                    if (b.displayString().equals(s)) {
                        blockType = b;
                        break;
                    }
                if (blockType != null) {
                    List<String> blocks = ChanBlocklist.getSorted(getActivity(), blockType);
                    (new BlocklistViewDialogFragment(blockType, blocks))
                            .show(getFragmentManager(),TAG);
                    dismiss();
                }
            }
        });
    }

}

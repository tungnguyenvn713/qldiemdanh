package com.nor.qldiemdanh.common;

import android.content.DialogInterface;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;

import com.nor.qldiemdanh.AppContext;
import com.nor.qldiemdanh.R;

public class PopupMenuUtils {
    public static void getPopupMenu(final View view, final ItemPopupClickListener listener, final int position) {
        if (!AppContext.getInstance().isAdmin) return;
        final PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
        if (popupMenu.getMenu().size() > 2) {
            popupMenu.getMenu().getItem(2).setVisible(false);
        }
        popupMenu.inflate(R.menu.popup_menu);

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.pop_delete:
                        DialogUtils.showDialogConfirm(view.getContext(), R.string.confirm_delete,
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        listener.onPopupDelete(position);
                                    }
                                });
                        break;
                    case R.id.pop_edit:
                        listener.onPopupEdit(position);
                        break;
                }
                return false;
            }
        });
        popupMenu.show();
    }

    public static void getPopupMenuStudent(final View view, final ItemPopupStudentClickListener listener, final int position) {
        if (!AppContext.getInstance().isAdmin) return;
        final PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
        popupMenu.inflate(R.menu.popup_menu);

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.pop_delete:
                        DialogUtils.showDialogConfirm(view.getContext(), R.string.confirm_delete,
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        listener.onPopupDelete(position);
                                    }
                                });
                        break;
                    case R.id.pop_edit:
                        listener.onPopupEdit(position);
                        break;
                    case R.id.pop_rest_pass:
                        listener.onPopupReset(position);
                        break;
                }
                return false;
            }
        });
        popupMenu.show();
    }

    public interface ItemPopupStudentClickListener {
        void onPopupEdit(int position);

        void onPopupDelete(int position);

        void onPopupReset(int position);
    }

    public interface ItemPopupClickListener {
        void onPopupEdit(int position);

        void onPopupDelete(int position);
    }
}

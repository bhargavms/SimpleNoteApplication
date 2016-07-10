package ms.bhargav.noteapp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ms.bhargav.noteapp.db.models.NoteRealmModel;

/**
 * Created by Bhargav on 7/7/2016.
 */
public class Mapper {
    public static NoteViewModel map(NoteRealmModel model) {
        return new NoteViewModel(model.getNote(), model.getTitle(),
                new Date(model.getCreateTime()),
                new Date(model.getModifiedTime()));
    }

    public static NoteRealmModel map(NoteViewModel model) {
        return new NoteRealmModel(model.getText(), model.getTitle(),
                model.getCreateDate().getTime(), model.getModifiedDate().getTime());
    }

    public static List<NoteViewModel> map(List<NoteRealmModel> models) {
        List<NoteViewModel> viewModels = new ArrayList<>(models.size());
        for (NoteRealmModel model : models) {
            viewModels.add(map(model));
        }
        return viewModels;
    }

    public static List<NoteRealmModel> mapReverse(List<NoteViewModel> viewModels) {
        List<NoteRealmModel> models = new ArrayList<>(viewModels.size());
        for (NoteViewModel viewModel : viewModels) {
            models.add(map(viewModel));
        }
        return models;
    }

}

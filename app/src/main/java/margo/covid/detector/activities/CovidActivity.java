package margo.covid.detector.activities;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.ArrayList;

import margo.covid.detector.R;
import margo.covid.detector.adapter.ItemsAdapter;
import margo.covid.detector.models.Classifier;
import margo.covid.detector.models.ListItem;

public class CovidActivity extends AppCompatActivity {

    public static Classifier.Recognition recognition;

    RecyclerView itemsList;
    CollapsingToolbarLayout toolbarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_covid);
        itemsList = findViewById(R.id.items_list);
        toolbarLayout = findViewById(R.id.collapsing_toolbar);
        loadSOI();
    }

    private void loadSOI() {
        if (recognition.getConfidence() < 0.7)
            initiateSOIView("Stay at home and tke the medicines", getPharmaciesList(), "Minor");
        else if (recognition.getConfidence() < 0.85)
            initiateSOIView("You need to follow up with a doctor", getDoctorsList(), "Moderate");
        else
            initiateSOIView("Please visit a hospital ASAP", getHospitalsList(), "Major");
    }

    private void initiateSOIView(String message, ArrayList<ListItem> items, String soi) {
        ItemsAdapter adapter = new ItemsAdapter(this, items);
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        itemsList.setAdapter(adapter);
        toolbarLayout.setTitle("SOI : " + soi);
    }

    private ArrayList<ListItem> getDoctorsList() {
        ArrayList<ListItem> items = new ArrayList<>();
        items.add(new ListItem("Dr. Adel Imam", R.drawable.a, "012 210 5396", "62 Soliman Gohar St.,Dokki, Cairo"));
        items.add(new ListItem("Dr. Ihab Attia", R.drawable.b, "012 210 2553", "92 El Tahrir St., Dokki Giza"));
        items.add(new ListItem("Dr. Sherif El-Mangoury", R.drawable.c, " 012 214 0029", "97 El Manyal St., Manial Cairo"));
        items.add(new ListItem("Dr. Galal M. El Said", R.drawable.d, "010 111 1516", "127 Mohamed Farid St.,Bostan Square "));
        items.add(new ListItem("Dr Hussein Hassan Rizk ", R.drawable.e, "+2 02 3749 8219", "25 Abdel Halim Hussein St, Mohandeseen, Giza"));
        items.add(new ListItem("Dr Medhat El Mofti", R.drawable.f, "+2 02 2794 4981 ", "71 Nobar St, Falaki Sq, 3rd Floor, Bab El Louk, Cairo"));
        items.add(new ListItem("Dr Abu Baker Attia Ali Shannon", R.drawable.g, "0111 0187 500", "73 Road 9, above Pizza Hut & Costa Café, Maadi, Cairo"));
        items.add(new ListItem("Dr Sherif Magdy Amin", R.drawable.h, "+2 02 2524 0250", "17 Mamaleek St, Heliopolis, Cairo"));
        return items;
    }

    private ArrayList<ListItem> getPharmaciesList() {
        ArrayList<ListItem> items = new ArrayList<>();
        items.add(new ListItem("AL'IMAGE Pharmacies", R.drawable.p1, " 02 36121023", "Giza- Al Wahat Al Baharia, Giza Governorate، Mall of Egypt، 6th of October، Giza Governorate "));
        items.add(new ListItem("Doss Pharmacies", R.drawable.p2, "02 24516016", "Street, Egypt, 11321, 17 Bani Tai, Cairo Governorate"));
        items.add(new ListItem("Fouda Pharmacies", R.drawable.p3, "02 33031481", "Giza, Mohandessin"));
        items.add(new ListItem("El Ezaby Pharmacy", R.drawable.p4, " 02 25756272", "Ramsis Square Egypt train station, Shop 104 A، Al Fagalah, Al Azbakeya, Cairo Governorate 11522\n"));
        items.add(new ListItem("Misr Pharmacies - Talaat Harb Branch", R.drawable.p5, "02 25476106", "44 Talaat Harb, Marouf, Abdeen, Cairo Governorate"));
        items.add(new ListItem("Bloom Pharmacy P90", R.drawable.p6, "0112 772 3339", "Shop 16 Ground Floor Point 90 Mall, Cairo Governorate 11835"));
        items.add(new ListItem("Al Fouad Pharmacies", R.drawable.p7, "02 37600630", "Behind Egypt Free Shops Co. - EFS، 4 Zamzam St. Off Gamaet El Dewal El Arabeya St، MOHANDESEEN"));
        items.add(new ListItem("AlMasry Pharmacies", R.drawable.p8, " 0106 955 5560", " 19 El-Shaikh Ahmed El-Sawy, Al Manteqah as Sadesah, Nasr City, 19 Cairo Governorate 11762 "));
        return items;
    }

    private ArrayList<ListItem> getHospitalsList() {
        ArrayList<ListItem> items = new ArrayList<>();
        items.add(new ListItem("Cairo Medical Centre", R.drawable.h1, "+20 2 450 9800", "4 Abou Obaida , Al Bakry , Roxy, Helioplis , Cairo , Egypt "));
        items.add(new ListItem("City Medical Clinic", R.drawable.h2, "+20 23 8571 591", "Hadaeq Al Ahram:Gate 4 (Mina) , Cairo , Egypt "));
        items.add(new ListItem("Cleopatra Hospital", R.drawable.h3, "+20 2 414 3931", "39 Cleopatra Street , Salah El Din Square , Helioplis , Cairo , Egypt"));
        items.add(new ListItem("Dr Magdi Ahmed Saeed Hospital", R.drawable.h4, "+20 11 11 08 2223", "32 Abu Dawood Elzahry Str , Cairo , Egypt"));
        items.add(new ListItem("El Salam International Hospital", R.drawable.h5, "+20 2 303 0501", "Cornich El Nil , PO Box 338 , Maadi , Cairo , Egypt"));
        items.add(new ListItem("Integrated Clinic", R.drawable.h6, "+20 65 355 5332", "59 Abdelmonem Road , 10th Floor, Flat 32 , El Mohandeseen , Cairo , Egypt"));
        items.add(new ListItem("Revive Medical Center", R.drawable.h7, "+20 109 555 5975", "47 Mohamed Mazhar St , 1st Floor , Zamalek , Cairo , Egypt"));
        items.add(new ListItem("Anglo American Hospital", R.drawable.h8, "+2 02 2735 6162", "1 Hadeeket El Zohreya Street, (opposite Cairo Tower), Zamalek, Cairo"));
        return items;
    }
}
package com.effigrity.Utilities;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;

import com.effigrity.ayurvedichomemedicine.free.MainSplashScreen;
import com.effigrity.ayurvedichomemedicine.free.R;
import com.google.android.gms.ads.InterstitialAd;
import java.util.HashMap;

public class CommonData {
    public static String baseUrl = "file:///android_asset";
    public static final HashMap<String, String> bodyPartProblems = new HashMap();
    public static String chosenBodyPart = "";
    public static int homeScreenNo = 0;
    public static InterstitialAd interstitial = null;
    public static final String interstitialId = "ca-app-pub-7199839993089502/6417501476";
    public static final String promotionId = "ca-app-pub-6914437447953704/7751230478";
    public static final HashMap<String, String> remedyItems = new HashMap();
    public static Typeface typeFace;
    public static String websiteBaseUrl = "http://effigrity.com/ayurvedic/";

    public static int getPagePosition(String value){
        switch (value.toLowerCase()) {
            case "ankle":
                return 0;
            case "babies":
                return 1;
            case "body":
                return 2;
            case "bones":
                return 3;
            case "ear":
                return 4;
            case "elbow":
                return 5;
            case "eye":
                return 6;
            case "face":
                return 7;
            case "hair":
                return 8;
            case "head":
                return 9;
            case "legs":
                return 10;
            case "lips":
                return 11;
            case "mouth":
                return 12;
            case "muscle":
                return 13;
            case "neck":
                return 14;
            case "nose":
                return 15;
            case "skin":
                return 16;
            case "stomach":
                return 17;
            case "throat":
                return 18;
            case "women":
                return 20;
            default:
                return -1;
        }
    }

    public static void intialize() {
        if (bodyPartProblems.size() == 0) {
            bodyPartProblems.put("Ankle", "Sprained Ankle");
            bodyPartProblems.put("Body", "Anaemia##Anxiety##Backache##Bedwetting##Depression##Detoxification##Fever##Gallstones##Hay fever##Herpes##High Cholesterol##Influenza##Insomnia##Jaundice##Kidney Stone##Measles##Morning sickness##Obesity & Weight Loss##Sleep Disorder##Vomiting##Weakness##Weight Gain");
            bodyPartProblems.put("Bones", "Joint Pain##Knee Pain##Osteoporosis");
            bodyPartProblems.put("Ear", "Earaches##Ear Infection##Tinnitus");
            bodyPartProblems.put("Elbow", "Tennis Elbow");
            bodyPartProblems.put("Eye", "Conjunctivitis##Eye Sty##Itchy Eyes");
            bodyPartProblems.put("Face", "Black Spots on Face##Blackheads##Dark Circles##Freckles##Milia##Pimples##Whiteheads##Wrinkles");
            bodyPartProblems.put("Feet", "Cracked Heels##Feet Corn##Foot Odour##Foot Pain##Gout##Shoe Bites");
            bodyPartProblems.put("Hair", "Dandruff##Dry Hair##Frizzy Hair##Hair Growth##Hair Loss##Premature Greying of Hair##Split Ends##Head Lice##Itchy Scalp##Managing Curly Hair##Oily Hair##To Straighten Hair");
            bodyPartProblems.put("Head", "Headache##Migraine##Nausea##Vertigo");
            bodyPartProblems.put("Legs", "Restless Legs");
            bodyPartProblems.put("Lips", "Chapped Lips##Dark Lips");
            bodyPartProblems.put("Mouth", "Bad Breath##Gum Disease##Mouth Ulcers##Toothache##Yellow Teeth");
            bodyPartProblems.put("Muscle", "Muscle Cramps##Muscle Pain##Muscle Strain");
            bodyPartProblems.put("Neck", "Neck Pain");
            bodyPartProblems.put("Nose", "Nasal Congestion##Runny Nose##Sinus Infection##Sneezing");
            bodyPartProblems.put("Skin", "Blemishes##Body Odour##Dry skin##Eczema##Fungal Infections##Ingrown Hair##Melasma##Moles##Mosquito Bites##Oily Skin##Peeling Skin##Prickly Heat##Psoriasis##Rashes##Rosacea##Sun Tanned Skin##Sunburn##Tinea Cruris##Warts");
            bodyPartProblems.put("Stomach", "Acid Reflux##Acidity##Constipation##Gas##Indigestion##Intestinal Worms##Irritable Bowel Syndrome (IBS)##Loss of Appetite##Stomach Ache##Stomach Flu##Stomach Ulcer##Upset Stomach");
            bodyPartProblems.put("Throat", "Cough##Goitre##Laryngitis##Pharyngitis##Sore Throat##Strep Throat##Tonsillitis");
            bodyPartProblems.put("Babies", "Cradle Cap##Diaper Rash##Mumps##Scabies");
            bodyPartProblems.put("Women", "Bacterial Vaginosis##Breast Enlargement##Fibroids##Irregular Periods##Vaginal Discharge (Leukorrhea)##Menstrual Cramps##Polycystic Ovary Syndrome");
        }
    }

    public static void prepareRemedyList() {
        if (remedyItems.size() == 0) {
            remedyItems.put("ankle/sprained_ankle", "Arnica##Epsom Salt##Garlic (Lahsun)##Onion (Pyaz)##Turmeric (Haldi)");
            remedyItems.put("babies/cradle_cap", "Baby Shampoo##Olive oil (Jaitun ka tel)##Petroleum Jelly##Almond Oil (Badam ka tel)##Apple Cider Vinegar");
            remedyItems.put("babies/mumps", "Aloe Vera (Guarphata)##Asparagus##Ginger (Adrak)##Indian Lilac (Neem)##Fenugreek Seeds (Methi)");
            remedyItems.put("babies/diaper_rash", "Vinegar (Sirka)##Petroleum Jelly##Corn-Starch##Coconut Oil (Nariyal tel)##Plantain Oil");
            remedyItems.put("babies/scabies", "Indian lilac (Neem)##Cayenne Pepper (Lal mirch)##Turmeric (Haldi)##White Vinegar (Sirka)##Aloe Vera (Guarphata)");
            remedyItems.put("body/anxiety", "Chamomile##Oranges (Santra)##Nutmeg (Jayfal)##Almonds (Badam)##Fennel (Sauf)");
            remedyItems.put("body/bedwetting", "Cinnamon (Dalchini)##Indian Gooseberry (Amla)##Cranberry Juice##Walnuts and Raisins##Apple Cider Vinegar");
            remedyItems.put("body/detoxification", "Juices##Lemons (Nimbu)##Epsom Salt Bath##Green Tea##Stinging Nettles");
            remedyItems.put("body/fever", "Cool Water##Basil (Tulsi)##Apple Cider Vinegar##Garlic (Lahsun)##Turmeric (Haldi)");
            remedyItems.put("body/hay_fever", "Local Honey (Shahad)##Ginger (Adrak)##Garlic (Lahsun)##Apple Cider Vinegar##Chamomile");
            remedyItems.put("body/influenza", "Honey (Shahad)##Lemon (Nimbu)##Ginger (Adrak)##Garlic (Lahsun)##Fenugreek (Methi)");
            remedyItems.put("body/morning_sickness", "Lemon (Nimbu)##Ginger (Adrak)##Peppermint##Fennel (Sauf)##Cheese");
            remedyItems.put("body/vomiting", "Ginger (Adrak)##Rice Water (Chawal ka pani)##Cinnamon (Dalchini)##Mint (Pudina)##Clove (Laung)");
            remedyItems.put("body/weakness", "Bananas (Kela)##Almonds (Badam)##Milk##Indian Gooseberry (Amla)##Eggs");
            remedyItems.put("body/anaemia", "Beetroot (Chukandara)##Spinach (Palak)##Pomegranate (Anaar)##Sesame Seeds (Til)##Dates (Khajoor)");
            remedyItems.put("body/backache", "Ginger (Adrak)##Basil Leaves (Tulsi)##Poppy Seeds (Khus Khus)##Garlic (Lahsun)##Ice Packs");
            remedyItems.put("body/depression", "St. John's Wort##Cardamom (Ilaychi)##Nutmeg (Jayfal)##Cashews (Kaju)##Apples");
            remedyItems.put("body/gallstones", "Apple Cider Vinegar##Lemon Juice##Peppermint##Vegetable Juice##Pears (Nashpati)");
            remedyItems.put("body/herpes", "Ice Packs##Apple Cider Vinegar##Hydrogen Peroxide##Garlic (Lahsun)##Aloe Vera (Guarphata)");
            remedyItems.put("body/high_cholesterol", "Coriander Seeds (Dhaniye ke beej)##Onions (Pyaz)##Indian Gooseberry (Amla)##Apple Cider Vinegar##Orange Juice (Santra)");
            remedyItems.put("body/kidney_stone", "Apple Cider Vinegar##Pomegranate (Anar)##Nettle Leaf##Basil (Tulsi)##Watermelon (Tarbuj)");
            remedyItems.put("body/insomnia", "Cumin Seeds (Jeera)##Nutmeg (Jayfal)##Saffron (Kesar)##Chamomile Tea##Hot Bath");
            remedyItems.put("body/jaundice", "Lemons (Nimbu)##Barley (Jau)##Turmeric (Haldi)##Ginger (Adrak)##Radishes (Muli)");
            remedyItems.put("body/obesity__weight_loss", "Lemon Juice (Nimbu)##Aloe Vera (Guarphata)##Green Tea##Cayenne Pepper (Lal mirch)##Tomatoes (Tamatar)");
            remedyItems.put("body/measles", "Neem Leaves##Olive Leaf##Bitter Gourd (Karela)##Barley (Jau)##Coconut Water");
            remedyItems.put("body/sleep_disorder", "Oil Massage##Epsom Salt Bath##Warm Milk##Banana (Kela)##Almonds (Badam)");
            remedyItems.put("body/weight_gain", "Raisins (Kishmish)##Clarified Butter (Ghee)##Mangoes (Aam)##Potatoes (Aalu)##Bananas (Kela)");
            remedyItems.put("bones/knee_pain", "Cold Compress##Apple Cider Vinegar##Cayenne Pepper (Lal Mirchi)##Ginger (Adrak)##Epsom Salt");
            remedyItems.put("bones/joint_pain", "Hot and Cold Compresses##Fenugreek Seeds (Methi)##Turmeric (Haldi)##Apple Cider Vinegar##Cayenne pepper (Lal Mirchi)");
            remedyItems.put("bones/osteoporosis", "Prunes (Dry Alu Bukhara)##Apples##Almond Milk##Sesame Seeds (Til)##Coriander (Dhaniya)");
            remedyItems.put("ear/tinnitus", "Ginkgo Biloba##Apple Cider Vinegar##Basil (Tulsi)##Onions (Pyaz)##Garlic (Lahsun)");
            remedyItems.put("ear/ear_infection", "Salt (Namak)##Garlic (Lahsun)##Basil (Tulsi)##Apple Cider Vinegar##Onion (Pyaz)");
            remedyItems.put("ear/earaches", "Olive Oil##Garlic (Lahsun)##Onion (Pyaz)##Ginger (Adrak)##Radish (Muli)");
            remedyItems.put("eye/eye_sty", "Coriander Seeds (Dhaniye ke beej)##Aloe Vera (Guarphata)##Guava Leaves##Potato (Aalu)##Cloves (Lavang)");
            remedyItems.put("eye/itchy_eyes", "Cucumber (Kakadi)##Cold Milk##Rosewater (Gulabjal)##Water and Salt##Aloe Vera (Guarphata)");
            remedyItems.put("eye/conjunctivitis", "Boric Acid##Black Tea##Water and Salt##Apple Cider Vinegar##Aloe Vera (Guarphata)");
            remedyItems.put("elbow/tennis_elbow", "Hot and Cold Compress##Turmeric (Haldi)##Ginger (Adrak)##Fenugreek (Methi)##Garlic (Lahsun)");
            remedyItems.put("face/whiteheads", "Facial Steam##Baking Soda##Sugar Scrub##Lemon Juice##Cinnamon powder (Dalchini)");
            remedyItems.put("face/milia", "Facial Steam##Honey (Shahad)##Pomegranate Peel Powder (Anar ke chilke ka powder)##Sugar Scrub##Aloe Vera (Guarphata)");
            remedyItems.put("face/dark_circles", "Cucumber (Kakadi)##Potato (Aalu)##Green Tea Bags##Rose Water (Gulabjal)##Tomato (Tamatar)");
            remedyItems.put("face/pimples", "Lemon Juice (Nimbu Juice)##Toothpaste##Garlic (Lahsun)##Steam##Turmeric (Haldi)");
            remedyItems.put("face/wrinkles", "Fenugreek (Methi)##Aloe Vera (Guarphata)##Ginger (Adrak)##Bananas (Kela)##Almonds (Badam)");
            remedyItems.put("face/black_spots_on_face", "Lemon Juice (Nimbu Juice)##Buttermilk (Chach)##Almonds (Badam)##Sandalwood (Chandan)##Potato (Aalu)");
            remedyItems.put("face/blackheads", "Baking Soda##Cinnamon (Dalchini)##Lemon Juice (Nimbu Juice)##Green Tea##Honey (Shahad)");
            remedyItems.put("face/freckles", "Lemon Juice (Nimbu Juice)##Red Onions (Lal Pyaz)##Buttermilk (Chach)##Papaya (Papita)##Cucumber (Kakadi)");
            remedyItems.put("feet/shoe_bites", "Honey (Shahad)##Rice Flour (Chawal ka aata)##Aloe Vera (Gaurpatha)##Turmeric and Indian Lilac (Neem)##Toothpaste");
            remedyItems.put("feet/feet_corn", "Pumice Stone##White Vinegar##Baking Soda##Garlic (Lahsun)##Papaya (Papita)");
            remedyItems.put("feet/foot_pain", "Vinegar (Sirka)##Epsom Salt##Clove Oil (Lavang oil)##Cayenne Pepper (Lal mirch)##Mustard Seeds (Sarso ke beej)");
            remedyItems.put("feet/cracked_heels", "Vegetable Oil##Indian Lilac (Neem)##Glycerine and Rosewater (Gulabjal)##Paraffin Wax##Banana (Kela)");
            remedyItems.put("feet/gout", "Apple Cider Vinegar##Ginger (Adrak)##Baking Soda##Epsom Salt##Bananas (Kela)");
            remedyItems.put("feet/foot_odour", "Baking Soda##Lavender Oil##Alum (Fitkari)##Epsom Salt##Ginger (Adrak)");
            remedyItems.put("hair/to_straighten_hair", "Hot Oil Treatments##Coconut Milk##Milk##Eggs and Olive Oil##Fuller's Earth (Multani Mitti)");
            remedyItems.put("hair/managing_curly_hair", "Apple Cider Vinegar##Beer##Eggs and Olive Oil##Avocado##Fenugreek Seeds (Methi)");
            remedyItems.put("hair/oily_hair", "Apple Cider Vinegar##Lemon Juice##Black Tea##Aloe Vera (Guarphata)##Fuller's Earth (Multani mitti)");
            remedyItems.put("hair/split_ends", "Egg##Avocado##Beer##Bananas (Kela)##Papaya (Papita)");
            remedyItems.put("hair/dry_hair", "Olive Oil##Mayonnaise##Eggs##Beer##Avocado");
            remedyItems.put("hair/frizzy_hair", "Carbonated Water (Soda)##Apple Cider Vinegar##Beer##Avocado##Coconut Milk");
            remedyItems.put("hair/hair_growth", "Rosemary##Castor Oil (Arandi oil)##Eggs##Indian Gooseberry (Amla)##Fenugreek (Methi)");
            remedyItems.put("hair/premature_greying_of_hair", "Indian Gooseberry (Amla)##Curry Leaves (Kadi patta)##Henna (Mehendi)##Rosemary and Sage##Onion Juice (Pyaz ka Juice)");
            remedyItems.put("hair/hair_loss", "Indian Gooseberry (Amla)##Fenugreek (Methi)##Onion Juice (Pyaz ka Juice)##Aloe Vera (Guarphata)##Coconut Milk");
            remedyItems.put("hair/dandruff", "Indian Lilac (Neem)##Apple Cider Vinegar##Baking Soda##White Vinegar##Fenugreek Seeds (Methi)");
            remedyItems.put("hair/head_lice", "Garlic (Lahsun)##Olive Oil##Salt (Namak)##White Vinegar##Sesame Seed Oil (Til ka tel)");
            remedyItems.put("hair/itchy_scalp", "Lemon Juice (Nimbu Juice)##Baking Soda##Apple Cider Vinegar##Aloe Vera (Guarphata)##Bananas (Kela)");
            remedyItems.put("head/vertigo", "Ginger (Adrak)##Coriander Seeds (Dhaniya)##Cardamom (Ilaychi)##Basil (Tulsi)##Almonds (Badam)");
            remedyItems.put("head/nausea", "Ginger (Adrak)##Peppermint##Lemon (Nimbu)##Clove (Lavang)##Cumin (Jeera)");
            remedyItems.put("head/migraine", "Apple Cider Vinegar##Ice Pack##Peppermint##Cayenne Pepper (Lal Mirchi)##Chamomile");
            remedyItems.put("head/headache", "Ginger (Adrak)##Basil (Tulsi)##Lavender Oil##Cloves (Lavang)##Cinnamon (Dalchini)");
            remedyItems.put("legs/restless_legs", "Apple Cider Vinegar##Hot and Cold Foot Soaks##Black Strap Molasses##Epsom Salt##Camphor Oil (Kapoor tel)");
            remedyItems.put("lips/chapped_lips", "Sugar and Honey (Chini and Shahad)##Rose Petals (Gulab ke patte)##Castor Oil (Arandi ka tel)##Milk Cream##Aloe Vera (Guarphata)");
            remedyItems.put("lips/dark_lips", "Lemon (Nimbu)##Olive Oil##Beetroot (Chukandar)##Pomegranate (Anar)##Cucumber (Kakadi)");
            remedyItems.put("mouth/mouth_ulcers", "Coconut Milk##Aloe Vera (Gaurpatha)##Coriander Seeds (Dhaniye ke beej)##Baking Soda##Honey (Shahad)");
            remedyItems.put("mouth/gum_disease", "Hydrogen Peroxide##Aloe Vera (Guarphata)##Oil Pulling##Myrrh (Lohban)##Sea Salt");
            remedyItems.put("mouth/yellow_teeth", "Baking Soda##Orange Peel (Santre ke chilte)##Strawberries##Hydrogen Peroxide##Lemon (Nimbu)");
            remedyItems.put("mouth/bad_breath", "Fennel (Sauf)##Cinnamon (Ilaychi)##Fenugreek (Methi)##Cloves (Lavang)##Lemon Juice (Nimbu Juice)");
            remedyItems.put("mouth/toothache", "Pepper and Salt (Kali mirch and salt)##Garlic (Lahsun)##Cloves (Lavang)##Onion (Pyaz)##Guava Leaves");
            remedyItems.put("muscle/muscle_pain", "Ice Pack##Turmeric (Haldi)##Ginger (Adrak)##Apple Cider Vinegar##Cayenne Pepper (Lal Mirchi)");
            remedyItems.put("muscle/muscle_cramps", "Epsom Salt##Apple Cider Vinegar##Clove Oil (Lavang Oil)##Black Strap Molasses##Rosemary");
            remedyItems.put("muscle/muscle_strain", "Ice Pack##Epsom Salt##Apple Cider Vinegar##Clove Oil (Lavang Oil)##Garlic (Lahsun)");
            remedyItems.put("neck/neck_pain", "Ice Pack##Hydrotherapy##Lavender Oil##Turmeric (Haldi)##Ginger (Adrak)");
            remedyItems.put("nose/sinus_infection", "Apple Cider Vinegar##Cayenne Pepper (Lal Mirchi)##Onion (Pyaz)##Garlic (Lahsun)##Ginger (Adrak)");
            remedyItems.put("nose/runny_nose", "Salt (Namak)##Mustard Oil (Sarso ka tel)##Garlic (Lahsun)##Eucalyptus Oil (Nilgiri ka tel)##Basil (Tulsi)");
            remedyItems.put("nose/sneezing", "Peppermint Oil (Pudina ka tel)##Black Pepper (Kali Mirch)##Ginger (Adrak)##Garlic (Lahsun)##Fenugreek Seeds (Methi ke beej)");
            remedyItems.put("nose/nasal_congestion", "Apple Cider Vinegar##Eucalyptus Oil (Nilgiri ka tel)##Herbal Tea##Tomato Juice##Fenugreek (Methi)");
            remedyItems.put("skin/peeling_skin", "Olive Oil (Jaitun ka tel)##Aloe Vera (Guarphata)##Milk##Lemon and Sugar Scrub##Cucumber (Kakadi)");
            remedyItems.put("skin/dry_skin", "Olive Oil (Jaitun ka tel)##Milk Cream (Malai)##Honey (Shahad)##Yogurt##Coconut Oil (Nariyal Tel)");
            remedyItems.put("skin/eczema", "Coconut Oil (Nariyal tel)##Colloidal Oatmeal##Turmeric (Haldi)##Indian Lilac (Neem)##Aloe Vera (Guarphata)");
            remedyItems.put("skin/blemishes", "Lemon Juice (Nimbu Juice)##Tomato Juice##Apple Cider Vinegar##Potato (Aalu)##Fuller's Earth (Multani mitti)");
            remedyItems.put("skin/prickly_heat", "Ice Pack##Sandalwood Powder (Chandan powder)##Fuller's Earth (Multani mitti)##Aloe Vera (Guarphata)##Cucumbers (Kakadi)");
            remedyItems.put("skin/body_odour", "Apple Cider Vinegar##Lemon Juice (Nimbu Juice)##Rosemary##Witch Hazel##Wheatgrass");
            remedyItems.put("skin/psoriasis", "Apple Cider Vinegar##Epsom Salt##Garlic (Lahsun)##Olive Oil (Jaitun tel)##Aloe Vera (Guarphata)");
            remedyItems.put("skin/fungal_infections", "Apple Cider Vinegar##Garlic (Lahsun)##Coconut Oil##Tea##Turmeric (Haldi)");
            remedyItems.put("skin/melasma", "Lemon Juice (Nimbu Juice)##Apple Cider Vinegar##Turmeric (Haldi)##Onion Juice (Pyaz ka Juice)##Aloe Vera Gel (Guarphata)");
            remedyItems.put("skin/rashes", "Olive Oil (Jaitun ka tel)##Aloe Vera (Guarphata)##Apple Cider Vinegar##Indian Lilac (Neem)##Coriander (Dhaniya)");
            remedyItems.put("skin/sunburn", "Aloe Vera (Guarphata)##Black Tea##Apple Cider Vinegar##Milk##Potatoes (Aalu)");
            remedyItems.put("skin/ingrown_hair", "Sugar##Tea Tree Oil##Salt##Aloe Vera (Guarphata)##Honey (Shahad)");
            remedyItems.put("skin/moles", "Garlic (Lahsun)##Apple Cider Vinegar##Iodine##Onion Juice (Pyaz ka Juice)##Aloe Vera (Guarphata)");
            remedyItems.put("skin/tinea_cruris", "Tea Tree Oil##Salt##Garlic (Lahsun)##Apple Cider Vinegar##Indian Lilac (Neem)");
            remedyItems.put("skin/oily_skin", "Egg Whites##Tomatoes (Tamatar)##Apples##Cucumbers (Kakadi)##Aloe Vera (Guarphata)");
            remedyItems.put("skin/sun_tanned_skin", "Lemon Juice (Nimbu Juice)##Aloe Vera (Guarphata)##Potato (Aalu)##Cucumber (Kakadi)##Sandalwood (Chandan)");
            remedyItems.put("skin/rosacea", "Chamomile##Lavender Oil##Honey (Shahad)##Apple Cider Vinegar##Aloe Vera (Guarphata)");
            remedyItems.put("skin/warts", "Garlic (Lahsun)##Apple Cider Vinegar##Baking Soda##Aloe Vera (Guarphata)##Basil (Tulsi)");
            remedyItems.put("skin/mosquito_bites", "Lemon (Nimbu)##Garlic (Lahsun)##Aloe Vera (Guarphata)##Salt##Toothpaste");
            remedyItems.put("stomach/stomach_ache", "Ginger (Adrak)##Fennel (Sauf)##Asafoetida (Hing)##Baking Soda and Lemon##Peppermint");
            remedyItems.put("stomach/stomach_flu", "Ginger (Adrak)##Peppermint##Cinnamon (Dalchini)##Apple Cider Vinegar##Garlic (Lahsun)");
            remedyItems.put("stomach/acid_reflux", "Baking Soda##Aloe Vera Juice (Guarphata Juice)##Yellow Mustard (Sarso)##Fennel seeds (Sauf)##Cumin (Jeera)");
            remedyItems.put("stomach/stomach_ulcer", "Cabbage (Patta Gobi)##Bananas (Kela)##Coconut (Nariyal)##Fenugreek (Methi)##Honey (Shahad)");
            remedyItems.put("stomach/intestinal_worms", "Coconut (Nariyal)##Garlic (Lahsun)##Indian Lilac (Neem)##Carrots (Gajar)##Cloves (Lavang)");
            remedyItems.put("stomach/irritable_bowel_syndrome", "Peppermint##Fennel Seeds (Sauf)##Cabbage Juice (Patta Gobi)##Chamomile##Carrots (Gajar)");
            remedyItems.put("stomach/upset_stomach", "Ginger (Adrak)##Apple Cider Vinegar##Bananas (Kela)##Fenugreek Seeds (Methi)##Fennel Seeds (Sauf)");
            remedyItems.put("stomach/loss_of_appetite", "Indian Gooseberry (Amla)##Ginger (Adrak)##Carom Seeds (Ajwain)##Garlic (Lahsun)##Coriander (Dhaniya)");
            remedyItems.put("stomach/gas", "Cinnamon (Dalchini)##Apple Cider Vinegar##Ginger (Adrak)##Garlic (Lahsun)##Fennel seeds (Sauf)");
            remedyItems.put("stomach/acidity", "Basil Leaves (Tulsi)##Cinnamon (Dalchini)##Buttermilk (Chach)##Apple Cider Vinegar##Cold Milk");
            remedyItems.put("stomach/constipation", "Lemon (Nimbu)##Fennel seeds (Sauf)##Honey (Sauf)##Grapes (Angoor)##Spinach (Palak)");
            remedyItems.put("stomach/indigestion", "Apple Cider Vinegar##Fennel Seeds (Sauf)##Ginger (Adrak)##Coriander (Dhaniya)##Cinnamon Powder (Dalchini)");
            remedyItems.put("throat/pharyngitis", "Apple Cider Vinegar##Cayenne Pepper (Lal Mirch)##Salt Water##Honey (Shahad)##Turmeric (Haldi)");
            remedyItems.put("throat/strep_throat", "Apple Cider Vinegar##Cayenne pepper (Lal mirch)##Garlic (Lahsun)##Salt Water##Lemon and Honey");
            remedyItems.put("throat/tonsillitis", "Salt Water##Lemon##Basil (Tulsi)##Turmeric (Haldi)##Cinnamon (Dalchini)");
            remedyItems.put("throat/goitre", "Iodine##Watercress##Dandelion (Sinhaparni)##Garlic (Lahsun)##Green Tea");
            remedyItems.put("throat/sore_throat", "Lemon##Apple Cider Vinegar##Cinnamon (Dalchini)##Salt Water##Garlic (Lahsun)");
            remedyItems.put("throat/laryngitis", "Apple Cider Vinegar##Ginger (Adrak)##Salt Water##Garlic (Lahsun)##Honey (Shahad)");
            remedyItems.put("throat/cough", "Turmeric (Haldi)##Ginger (Adrak)##Garlic (Lahsun)##Onion (Pyaz)##Carrot Juice (Gajar)");
            remedyItems.put("women/vaginal_discharge", "Apple Cider Vinegar##Fenugreek Seeds (Methi)##Indian Gooseberry (Amla)##Banana (Kela)##Cranberry Juice");
            remedyItems.put("women/breast_enlargement", "Exercises##Fenugreek (Methi)##Fennel Seeds (Sauf)##Wheat Germ Oil (Gehu ke beej ka tel)##Wild Yam (Ratalu)");
            remedyItems.put("women/menstrual_cramps", "Heating pad##Ginger (Adrak)##Basil (Tulsi)##Cinnamon (Dalchini)##Fennel seeds (Sauf)");
            remedyItems.put("women/irregular_periods", "Ginger (Adrak)##Cinnamon (Dalchini)##Aloe Vera (Guarphata)##Turmeric (Haldi)##Fennel seeds (Sauf)");
            remedyItems.put("women/bacterial_vaginosis", "Apple Cider Vinegar##Yogurt##Garlic (Lahsun)##Fenugreek (Methi)##Indian Lilac (Neem)");
            remedyItems.put("women/polycystic_ovary_syndrome", "Cinnamon (Dalchini)##Apple Cider Vinegar##Fenugreek seeds (Methi)##Fish Oil##Holy Basil (Tulsi)");
            remedyItems.put("women/fibroids", "Dandelion (Sinhaparni)##Green Tea##Milk##Garlic (Lahsun)##Indian Gooseberry (Amla)");
        }
    }

    public static void sendNotification(Context context, String title, String message){
        Intent notificationIntent = new Intent(context, MainSplashScreen.class);
        notificationIntent.putExtra("notification_action",title);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        Log.d("fcm","Received push message => "+title);

        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        NotificationCompat.Builder b = new NotificationCompat.Builder(context);

        b.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle(title)
                .setDefaults(Notification.DEFAULT_LIGHTS| Notification.DEFAULT_SOUND)
                .setContentIntent(contentIntent)
                .setContentText(message)
                .setContentInfo("Info");


        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, b.build());
    }
}

package com.effigrity.ayurvedichomemedicine.free;

import android.os.Bundle;
import android.widget.TextView;

public class AboutUs extends DashboardActivity {
    TextView disclaimer;
    String remedytext = "1. Epsom salt: Epsom salt is not recommended for those suffering from high blood pressure, diabetes and heart problems.\n2. Garlic: Garlic remedies are not recommended for pregnant ladies and small children.\n3. Cinnamon: Pregnant ladies should avoid Cinnamon remedies.\n4. Ice pack: Never apply ice directly to the skin.\n5. Baking soda: Avoid usage of baking soda on sensitive skin. People with high blood pressure should also avoid consumption of baking soda.\n6. Aloe Vera: Do not use Aloe Vera during periods.\n7. Cinnamon powder: Regular consumption should be consulted with doctor as it can reduce blood sugar level.\n8. Holy Basil: If you are taking diabetic medication, consult a doctor before taking this herb on a continuous basis as it reduces blood glucose level.\n9. Indian Lilac: Pregnant women or women who are breastfeeding should not consume Indian Lilac.\n10. Ginger: People with high blood pressure should avoid ginger remedies.";
    TextView remedywarning;
    String text = "1. All the information mentioned on this application like text and images are solely for informational and educational purposes only. The publication of information mentioned here does not constitute the practice of medicine, and all the medical advices and suggestions mentioned here do not replace the advice of your physician or doctor.\n2.The remedies and suggestions are provided without warranty of any kind, whether express or implied. If you would like to try any of these remedies, you need to ask your doctor first. You also expressly agree that use of the treatments mentioned here is at your sole risk and discretion.\n3.\tAny statements made in this application about products may have not been evaluated by any authorized organization. If you rely on any content obtained by you on or through this application, you do so solely at your own risk.\n4.\tThe owners make no warranty that the content or information available on this application will be uninterrupted, timely, secure or error-free.  There may be technical mistakes, inaccuracies or typographical errors and hence the owners reserve the right to change the content and information at any time without notice.\n5.\tWhile using this app, you agree that in no event shall \u201cNature and Ayurveda\u201d be liable to you or anyone else for any personal injury, including death, caused by your use or misuse of this application or the content. This application is not liable to you or any other party for any loss or damages resulting from any claims, demands, or actions arising out of or relating to the information mentioned on this application.\n6.\tYou acknowledge and agree that all content and information on the application is protected by proprietary rights and laws. You agree not to copy, distribute, reproduce, publish, and create any kind of derivate work from any content or information obtained from or through this application.";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_us);
        this.disclaimer = (TextView) findViewById(R.id.disclaimer);
        this.disclaimer.setText(this.text);
        this.remedywarning = (TextView) findViewById(R.id.remedywarning);
        this.remedywarning.setText(this.remedytext);
    }
}

package com.nuri.firebaseauth.views

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.nuri.firebaseauth.databinding.FragmentBottomSheetBinding
import kotlinx.android.synthetic.main.fragment_bottom_sheet.*


class BottomSheet : BottomSheetDialogFragment() {
    private var _binding: FragmentBottomSheetBinding? = null
    private val binding get() = _binding!!
    val db = Firebase.firestore



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBottomSheetBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.srBtn.setOnClickListener {
            val spservice = spin_service.getSelectedItem().toString().trim()
            val srtitle = sr_titleinput.text.toString().trim()
            val sppayment = spin_payment.getSelectedItem().toString().trim()
            val srdepart = sr_departinput.text.toString().trim()
            val srdstion = sr_destinationinput.text.toString().trim()
            val fee = sr_feeinput.text.toString().trim()
            var hsdata = hashMapOf("spservice" to spservice, "sppayment" to sppayment, "srtitle" to srtitle,"srdepart" to srdepart
            ,"fee" to fee, "srdstion" to srdstion)
            if(spservice.isEmpty() || srtitle.isEmpty() || sppayment.isEmpty() || srdstion.isEmpty() || fee.isEmpty()) {
                Toast.makeText(activity, "공란이 없는지 다시 한번 확인해주세요.", Toast.LENGTH_SHORT).show()
            }else {
                db.collection("hshelper").document("hs").set(hsdata)
                Toast.makeText(activity, "정상적으로 등록이 완료되었습니다.",Toast.LENGTH_SHORT).show()
                val intent = Intent(activity,ListActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}


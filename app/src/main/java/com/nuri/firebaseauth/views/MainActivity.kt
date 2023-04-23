package com.nuri.firebaseauth.views

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.nuri.firebaseauth.R
import kotlinx.android.synthetic.main.main.*

class MainActivity : AppCompatActivity(), OnMapReadyCallback {
    private var googleMap: GoogleMap? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)

        reqlists.setOnClickListener {
            val intent = Intent(this, ListActivity::class.java)
            startActivity(intent)
        }

        val button = findViewById<Button>(R.id.request)
        button.setOnClickListener {
            val bottomSheet = BottomSheet()
            bottomSheet.show(supportFragmentManager, bottomSheet.tag)
        }

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)
        findViewById<View>(R.id.information).setOnClickListener { view ->
            val popupMenu = PopupMenu(applicationContext, view)
            menuInflater.inflate(R.menu.popup, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener { menuItem ->
                if (menuItem.itemId == R.id.action_menu1) {
                    Toast.makeText(this@MainActivity, "메뉴 1 클릭", Toast.LENGTH_SHORT).show()
                } else if (menuItem.itemId == R.id.action_menu2) {
                    Toast.makeText(this@MainActivity, "메뉴 2 클릭", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@MainActivity, "메뉴 3 클릭", Toast.LENGTH_SHORT).show()
                }
                false
            }
            popupMenu.show()
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap
        val latLng = LatLng(36.69087, 126.58080)
        //한서대학교 위치
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng))
        googleMap.moveCamera(CameraUpdateFactory.zoomTo(15f))
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            googleMap.isMyLocationEnabled = true
        } else {
            checkLocationPermissionWithRationale()
        }
    }

    private fun checkLocationPermissionWithRationale() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {
                AlertDialog.Builder(this)
                    .setTitle("위치정보")
                    .setMessage("이 앱을 사용하기 위해서는 위치정보에 접근이 필요합니다. 위치정보 접근을 허용하여 주세요.")
                    .setPositiveButton("확인") { dialogInterface, i ->
                        ActivityCompat.requestPermissions(
                            this@MainActivity, arrayOf(
                                Manifest.permission.ACCESS_FINE_LOCATION
                            ), MY_PERMISSIONS_REQUEST_LOCATION
                        )
                    }
                    .create().show()
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    MY_PERMISSIONS_REQUEST_LOCATION
                )
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_LOCATION -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(
                            this,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        googleMap!!.isMyLocationEnabled = true
                    }
                } else {
                    Toast.makeText(this, "접근이 거부되었습니다.", Toast.LENGTH_LONG).show()
                }
                return
            }
        }
    }

    companion object {
        const val MY_PERMISSIONS_REQUEST_LOCATION = 99
    }
}
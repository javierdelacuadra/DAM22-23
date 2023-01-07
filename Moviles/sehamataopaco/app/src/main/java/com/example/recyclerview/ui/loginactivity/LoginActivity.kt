package com.example.recyclerview.ui.loginactivity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.example.recyclerview.ui.loginactivity.fragments.logindoctor.LoginDoctorFragment
import com.example.recyclerview.ui.loginactivity.fragments.loginusuario.LoginUsuarioFragment
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.recyclerview.R.layout.activity_login)
        setUpTabs()
        supportActionBar?.hide()
    }

    private fun setUpTabs() {
        val viewPager = findViewById<ViewPager>(com.example.recyclerview.R.id.viewPager)
        val pagerAdapter = MyPagerAdapter(supportFragmentManager)
        pagerAdapter.addFragment(LoginUsuarioFragment(), "Acceso Usuario")
        pagerAdapter.addFragment(LoginDoctorFragment(), "Acceso Doctor")
        viewPager.adapter = pagerAdapter
        val tabLayout = findViewById<TabLayout>(com.example.recyclerview.R.id.tabLayout)
        tabLayout.setupWithViewPager(viewPager)
    }

}
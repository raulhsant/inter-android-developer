package me.estudos.contactlist

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.edit
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MainActivity : AppCompatActivity(), ClickItemContactListener {
    private val rvList: RecyclerView by lazy { findViewById(R.id.rv_list) }
    private val adapter = ContactAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.drawer_menu)

        fetchContactList()
        initDrawer()
        bindViews()
    }

    private fun fetchContactList() {
        val list = arrayListOf(
            Contact("Raul Santana", "(00) 90000-0000", "img.png"),
            Contact("Talita Santos", "(00) 99999-9999", "img.png")
        )

        getSharedPreferencesInstance().edit {
            putString("contacts", Gson().toJson(list))
            commit()
        }
    }

    private fun getSharedPreferencesInstance(): SharedPreferences {
        return getSharedPreferences("me.estudos.contactlist.PREFERENCES", Context.MODE_PRIVATE)
    }

    private fun initDrawer() {
        val drawerLayout = findViewById<View>(R.id.drawer_layout) as DrawerLayout
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.open_drawer,
            R.string.close_drawer
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
    }

    private fun bindViews() {
        rvList.adapter = adapter
        rvList.layoutManager = LinearLayoutManager(this)
        updateList()
    }

    private fun getContactList(): List<Contact> {
        val listString = getSharedPreferencesInstance().getString("contacts", "[]")
        val type = object : TypeToken<List<Contact>>() {}.type

        return Gson().fromJson(listString, type)
    }

    private fun updateList() {
        adapter.updateList(getContactList())
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.item_menu_1 -> {
                showToast("Click no item de menu 1")
                return true
            }
            R.id.item_menu_2 -> {
                showToast("Click no item de menu 2")
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun clickItemContact(contact: Contact) {
        val intent = Intent(this, ContactDetailActivity::class.java)
        intent.putExtra(ContactDetailActivity.EXTRA_CONTACT, contact)
        startActivity(intent)
    }
}
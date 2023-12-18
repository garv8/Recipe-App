package com.garv.bookhub

import android.app.Activity
import android.app.AlertDialog
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.*
import android.widget.Button
import android.view.Menu
import android.view.View.inflate
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.core.app.ActivityCompat
import androidx.core.view.DragAndDropPermissionsCompat.request
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Request.Method.*
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import model.Book
import org.json.JSONException
import util.ConnectionManager
import java.io.IOException
import java.net.MalformedURLException
import java.util.*
import kotlin.collections.HashMap


class DashboardFragment : Fragment() {
    lateinit var recyclerDashboard:RecyclerView
    lateinit var btnCheckInternet: Button
    lateinit var layoutManager: RecyclerView.LayoutManager
    var bookInfoList= arrayListOf<Book>()
    var tempArrayList= arrayListOf<Book>()

    var searchText=""
    var url=""
    lateinit var recyclerAdapter: DashboardRecyclerAdapter
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search, menu)
        val item=menu?.findItem(R.id.search_action)
        val searchView=item?.actionView as SearchView
        searchView.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    val queue = Volley.newRequestQueue(activity as Context)
                    searchText = query
                    println("searchText=$searchText")
                    //val url2 = "https://api.spoonacular.com/recipes/complexSearch?apiKey=**&query=$searchText"
                    val url2 = "https://www.themealdb.com/api/json/v1/1/search.php?s=$searchText"
                    println(url2)
                    tempArrayList.clear()
                    //bookInfoList.clear()
                    recyclerDashboard.adapter!!.notifyDataSetChanged()
                    try {
    val jsonObjectRequest = object :
        JsonObjectRequest(Request.Method.GET, url2, null, Response.Listener {
            /*val success = it.getBoolean("success")*/
            if(it.has("meals") && !it.isNull("meals") ) {
            val results = it.getJSONArray("meals")
            for (i in 0 until results.length()) {

                val bookJsonObject = results.getJSONObject(i)
                val id = bookJsonObject.getString("idMeal")
                //val a = id.toString()
                val bookObject = Book(
                    "hi",
                    bookJsonObject.getString("strMeal"),
                    bookJsonObject.getString("strSource"),
                    "bye",
                    "sigh",

                    bookJsonObject.getString("strMealThumb")
                )
                bookInfoList.add(bookObject)
                tempArrayList.add(bookObject)
                recyclerAdapter =
                    DashboardRecyclerAdapter(activity as Context, tempArrayList)
                recyclerDashboard.adapter = recyclerAdapter
                recyclerDashboard.layoutManager = layoutManager

            }}
        }, Response.ErrorListener { }) {
        override fun getHeaders(): MutableMap<String, String> {
            val headers = HashMap<String, String>()
            headers["Content-type"] = "application/json"
            headers["token"] = "YOUR_TOKEN_HERE"

            return headers
        }

    }
    queue.add(jsonObjectRequest)

}
catch (e: MalformedURLException) {
    e.printStackTrace();
} catch (e: IOException) {
    e.printStackTrace();
}
catch (e:NullPointerException) {
    e.printStackTrace();
} catch (e:Exception) {
    e.printStackTrace();
}
catch (e:JSONException){
    e.printStackTrace()
                    }
                }

                return false


            }

            override fun onQueryTextChange(newText: String?): Boolean {

                tempArrayList.clear()
                searchText=newText!!.toLowerCase(Locale.getDefault())
                if(searchText.isNotEmpty()){
                    bookInfoList.forEach {

                        if(it.bookName.toLowerCase(Locale.getDefault()).contains(searchText)){
                            tempArrayList.add(it)
                        }
                    }
                    recyclerDashboard.adapter!!.notifyDataSetChanged()
                }
                else{
                    tempArrayList.clear()
                    tempArrayList.addAll(bookInfoList)
                    recyclerDashboard.adapter!!.notifyDataSetChanged()
                }
                return false
            }


        })
        super.onCreateOptionsMenu(menu, inflater)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view=inflater.inflate(R.layout.fragment_dashboard, container, false)
        recyclerDashboard=view.findViewById(R.id.recyclerDashboard)
        btnCheckInternet=view.findViewById(R.id.btnCheckInternet)
        btnCheckInternet.setOnClickListener {
            if(ConnectionManager().checkConnectivity(activity as Context)){
                val dialog=AlertDialog.Builder(activity as Context)
                dialog.setTitle("Success")
                dialog.setMessage("App connected to internet")
                dialog.setPositiveButton("Ok"){text, listener->}
                dialog.setNegativeButton("Cancel"){text, listener->}
                dialog.create()
                dialog.show()
            }
            else{
                val dialog=AlertDialog.Builder(activity as Context)
                dialog.setTitle("Error")
                dialog.setMessage("Internet connection not found!")
                dialog.setPositiveButton("Ok"){text, listener->}
                dialog.setNegativeButton("Cancel"){text, listener->}
                dialog.create()
                dialog.show()

            }
        }
        layoutManager=LinearLayoutManager(activity)
        tempArrayList.clear()
        bookInfoList.clear()
        val queue= Volley.newRequestQueue(activity as Context)
        val queue1= Volley.newRequestQueue(activity as Context)

        if (!searchText.equals("")) {
            url = "https://api.spoonacular.com/recipes/complexSearch?apiKey=**&query=$searchText"

        }
        else
        {
            url="https://api.spoonacular.com/recipes/complexSearch?apiKey=**&query=indian"
        }
        println(url)

        if(ConnectionManager().checkConnectivity(activity as Context)) {
            val jsonObjectRequest = object : JsonObjectRequest(Request.Method.GET, url, null, Response.Listener {
                /*val success = it.getBoolean("success")*/

                val results = it.getJSONArray("results")//results=data

                for (i in 0 until results.length()) {

                    val bookJsonObject = results.getJSONObject(i)
                    val id=bookJsonObject.getInt("id")
                    val a=id.toString()
                    val url1="https://api.spoonacular.com/recipes/$a/information?apiKey=**&id=$a"
                    //print(url1)
                    val jsonObjectRequest1 = object : JsonObjectRequest(Request.Method.GET, url1, null, Response.Listener {it ->

                        val bookObject = Book(
                            "hi",
                            bookJsonObject.getString("title"), it.getString("creditsText"),a, "sigh",

                            bookJsonObject.getString("image")
                        ) // testing with returned and mock data

                        if(!bookInfoList.contains(Book("hi", bookJsonObject.getString("title"), it.getString("creditsText"),a, "sigh",

                                bookJsonObject.getString("image")))){
                            bookInfoList.add(bookObject)
                            tempArrayList.add(bookObject)
                        }

                        recyclerAdapter = DashboardRecyclerAdapter(activity as Context, tempArrayList)
                        recyclerDashboard.adapter = recyclerAdapter
                        recyclerDashboard.layoutManager = layoutManager
                        recyclerDashboard.addItemDecoration(
                            DividerItemDecoration(recyclerDashboard.context, (layoutManager as LinearLayoutManager).orientation))
                    } ,Response.ErrorListener {

                    }){override fun getHeaders(): MutableMap<String, String> {
                        val headers = HashMap<String, String>()
                        headers["Content-type"] = "application/json"
                        headers["token"] = "YOUR_TOKEN_HERE"

                        return headers
                    }}

                    queue1.add(jsonObjectRequest1)

                }

            }, Response.ErrorListener { }) {
                    override fun getHeaders(): MutableMap<String, String> {
                        val headers = HashMap<String, String>()
                        headers["Content-type"] = "application/json"
                        headers["token"] = "YOUR_TOKEN_HERE"
                        return headers
                    }

                }
            queue.add(jsonObjectRequest)
        }
        else{
            val dialog=AlertDialog.Builder(activity as Context)
            dialog.setTitle("Error")
            dialog.setMessage("Internet connection not found!")
            dialog.setPositiveButton("Open Settings"){text, listener->
                val settingsIntent= Intent(Settings.ACTION_WIRELESS_SETTINGS)
                startActivity(settingsIntent)
                activity?.finish()
            }
            dialog.setNegativeButton("Exit"){text, listener->
                ActivityCompat.finishAffinity(activity as Activity)
            }
            dialog.create()
            dialog.show()
        }


        return view}



}

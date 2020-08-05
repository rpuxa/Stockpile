package ru.rpuxa.stockpile

import android.view.View
import androidx.test.rule.ActivityTestRule
import com.agoda.kakao.common.views.KView
import com.agoda.kakao.edit.KEditText
import com.agoda.kakao.recycler.KRecyclerItem
import com.agoda.kakao.recycler.KRecyclerView
import com.agoda.kakao.screen.Screen
import com.agoda.kakao.screen.Screen.Companion.onScreen
import com.agoda.kakao.text.KTextView
import kotlinx.coroutines.runBlocking
import org.hamcrest.Matcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import ru.rpuxa.stockpile.model.Product
import ru.rpuxa.stockpile.ui.App
import ru.rpuxa.stockpile.ui.AppActivity
import java.lang.Thread.sleep


class UITest {

    @get:Rule
    val rule = ActivityTestRule(AppActivity::class.java)

    @Before
    fun before() {
        App.component.productDao.clear()
    }

    private class MainScreen : Screen<MainScreen>() {
        val recycler = KRecyclerView(
            builder = {
                withId(R.id.recyclerView)
            },
            itemTypeBuilder = {
                itemType(::ProductItem)
            }
        )
        val addProduct = KView {
            withId(R.id.addProduct)
        }
        val name = KEditText {
            withId(R.id.name)
        }
        val price = KEditText {
            withId(R.id.price)
        }
        val accept = KView {
            withId(R.id.accept)
        }
    }

    private class ProductItem(parent: Matcher<View>) : KRecyclerItem<ProductItem>(parent) {
        val name = KTextView {
            withId(R.id.name)
        }
        val price = KTextView {
            withId(R.id.price)
        }
        val geo = KView {
            withId(R.id.geo)
        }
    }

    @Test
    fun add_new_product() {
        sleep(1000)
        onScreen<MainScreen> {
            recycler.hasSize(0)
            addProduct.click()
            sleep(500)
            name.hasAnyText()
            name.clearText()
            name.typeText("Test")
            price.hasAnyText()
            price.clearText()
            price.typeText("1488.0")
            accept.click()
            sleep(500)
            recycler.hasSize(1)
            recycler.firstChild<ProductItem> {
                name.hasText("Test")
                price.hasText("1488.00$")
            }
        }
    }

    @Test
    fun check_product_edition() {
        runBlocking {
            App.component.productDao.insertAll(listOf(
                Product(1, "Name", 100.0, 0, null)
            ))
        }
        sleep(1000)
        onScreen<MainScreen> {
            recycler.hasSize(1)
            recycler.firstChild<ProductItem> {
                name.hasText("Name")
                price.hasText("100.00$")
                geo.isGone()
                click()
            }
            sleep(500)
            name.hasAnyText()
            name.clearText()
            name.typeText("Test")
            price.hasAnyText()
            price.clearText()
            price.typeText("1488.0")
            accept.click()
            sleep(500)
            recycler.hasSize(1)
            recycler.firstChild<ProductItem> {
                name.hasText("Test")
                price.hasText("1488.00$")
                geo.isGone()
            }
        }
    }
}
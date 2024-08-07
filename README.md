News App
========

News  app is a demo app that shows news from newsorg api service. It uses Everything and top headlines api. 
Most of the times api does not return proper data , for eg image, title , desription or content  property comes as null or empty.

Functionality of the app 
=========================
App has a bottom navigation which consists of 3 tabs, Home,Categories and Bookmarks. 
When the app is launched Home screen is displayed which calls everything api service and displays data on the screen. 
Clicking on a new item displays details screen , top navigation consits of fav icon and "info" icon. fav icon adds that news item in room database and i icon opens the news in the browser. 
Categories tab displays list of predefined categories from newsorg api, clicking of a specific category opens a new screen which displayes news belonging to that category using topHeadlines Api. 
Bookmark screen shows list of bookmark added by user,clicking on a bookmark opens a dialog through which user can delete a bookmark.


Implementation Details 
=======================
App Build completely using jetpack compose
Most compose functions are stateless
Separation of Concerns 
MVVM pattern 
Manual Dependency Injection (As app is a small one  i have used MD , otherwise hilt can be used)
Unit testing of ViewModels , Repos and ApiService.
Instrumentation testing of Ui components,screens,  navigation controller. 


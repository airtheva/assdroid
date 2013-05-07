assdroid
========
Hello everyone, this is a project which takes up more than a month of my last senior high school term. I must prepare for the coming final exam now, so I post all the source code here, in the hope that someone may be interested in it. I do not know so much about licenses, but I wish that whoever interested in it would contact me. Forking is welcomed. Thanks a lot.

AssDroid, is a 3D model viewer for Android. My first aim is to create one better than "HD Model Viewer" in the market, but instead of using Unity3D, I implement the drawing logic on my own. The 3rd-party libraries used in AssDroid is listed below. (If any licenses is missing, please contact me!)
Java:
ActionBarSherlock
SlidingMenu
C/C++:
Assimp, an amazing 3D asset import library, which play as the core of AssDroid.
DevIL, an all-in-one image library, used to load various types of textures.
libpng, works with DevIL, providing .png support.

(C/C++ libraries are included via prebuilts or sources, for Assimp and libpng were built by cmake, while DevIL by Android.mk.

The current version is 0.2.2-milestone and the UI is not so user-friendly, but anyway, it does what it should do! One can load several models into the world, translating, rotating or scaling a model from the context menu and the viewer can rotate and move around (using hardware keyboard) as well.

Known issues:
Loading many models might cause texture conflict.

Roadmap:
There are so many things I want to do. For example, joystick for moving around, animations, saving / loading the world state to / from an external file, etc. I will be back as soon as the final exam is finished!

Downloads can be found in the homepage below.

Homepage: http://airtheva.net/
Twitter: @airtheva
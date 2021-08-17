## TODO migrar a compose


<com.chemasmas.customcomponentslibrary.components.CurvedBottomNavigationView
            android:id="@+id/curvedBottomNavigationView2"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            app:layout_constraintBottom_toBottomOf="parent"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintStart_toStartOf="parent"
            app:menu="@menu/botton_menu"
            app:radioSize="45dp" />

    <com.google.android.material.floatingactionbutton.FloatingActionButton
            android:id="@+id/floatingActionButton"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_marginBottom="16dp"
            android:clickable="true"
            android:visibility="visible"
            app:fabCustomSize="80dp"
            app:fabSize="normal"
            app:layout_constraintBottom_toBottomOf="parent"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintStart_toStartOf="parent"
            app:maxImageSize="35dp"
            app:srcCompat="@android:drawable/ic_btn_speak_now" />

    <com.chemasmas.customcomponentslibrary.components.CurvedBottomNavigationView
            android:id="@+id/curvedBottomNavigationView"
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_marginBottom="8dp"
            app:layout_constraintBottom_toTopOf="@+id/floatingActionButton"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintStart_toStartOf="parent"
            app:radioSize="32dp">

    </com.chemasmas.customcomponentslibrary.components.CurvedBottomNavigationView>

    <com.google.android.material.floatingactionbutton.FloatingActionButton
            android:id="@+id/floatingActionButton2"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_marginBottom="28dp"
            android:clickable="true"
            app:fabSize="normal"
            app:layout_constraintBottom_toBottomOf="@+id/curvedBottomNavigationView"
            app:layout_constraintEnd_toEndOf="@+id/curvedBottomNavigationView"
            app:layout_constraintHorizontal_bias="0.5"
            app:layout_constraintStart_toStartOf="@+id/curvedBottomNavigationView"
            app:srcCompat="@drawable/ic_launcher_foreground" />

    <com.chemasmas.customcomponentslibrary.components.CurvedBottomNavigationView
            android:id="@+id/curvedBottomNavigationView3"
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_marginBottom="8dp"
            app:layout_constraintBottom_toTopOf="@+id/floatingActionButton2"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintStart_toStartOf="parent"
            app:radioSize="23dp" />

    <com.google.android.material.floatingactionbutton.FloatingActionButton
            android:id="@+id/floatingActionButton3"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_marginBottom="36dp"
            android:clickable="true"
            app:fabSize="mini"
            app:layout_constraintBottom_toBottomOf="@+id/curvedBottomNavigationView3"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintStart_toStartOf="parent"
            app:srcCompat="@drawable/ic_launcher_foreground" />
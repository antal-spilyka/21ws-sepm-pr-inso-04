import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { debounceTime, distinctUntilChanged, Observable, switchMap } from 'rxjs';
import { Artist } from 'src/app/dtos/artist';
import { Category } from 'src/app/dtos/category';
import { ArtistService } from 'src/app/services/artist.service';
import { CategoryService } from 'src/app/services/category.service';

@Component({
  selector: 'app-create-artist',
  templateUrl: './create-artist.component.html',
  styleUrls: ['./create-artist.component.scss']
})
export class CreateArtistComponent implements OnInit {

  @Input() handleNext: (values: any) => void;

  artists: Observable<Artist[]>;
  categories: Observable<Category[]>;
  selectedArtist: Artist;
  selectedCategory: Category;
  isNewArtist = false;
  isNewCategory = false;

  form = this.formBuilder.group({
    name: [null],
    description: [{value: null, disabled: true}],
    categoryName: [null]
  });

  constructor(
    private formBuilder: FormBuilder,
    private artistService: ArtistService,
    private categoryService: CategoryService
    ) {
    this.artists = this.form.get('name').valueChanges.pipe(
      distinctUntilChanged(),
      debounceTime(500),
      switchMap(name => !this.isNewArtist ?
        this.artistService.findArtist(name) :
        new Observable<Artist[]>()
    )
  );
  this.categories = this.form.get('categoryName').valueChanges.pipe(
    distinctUntilChanged(),
    debounceTime(500),
    switchMap(name => !this.isNewCategory ?
      this.categoryService.findCategory(name) :
    new Observable<Category[]>()
  )
  );
   }

  ngOnInit(): void {
  }

  handleNewArtist() {
    this.isNewArtist = !this.isNewArtist;
    if(this.isNewArtist) {
      this.selectedArtist = null;
      this.form.controls.name.setValue(null);
      this.form.controls.description.enable();
    } else {
      this.form.controls.description.disable();
    }
  }

  handleNewCategory() {
    this.isNewCategory = !this.isNewCategory;
    if(this.isNewCategory) {
      this.selectedCategory = null;
      this.form.controls.categoryName.setValue(null);
    }
  }

  nextStep() {
    const selectedArtist = this.selectedArtist;
    const selectedCategory = this.selectedCategory;
    this.handleNext({selectedArtist, selectedCategory});
  }

  onSelectArtist(artist: Artist) {
    const { firstName, description } = artist;
    this.selectedArtist = artist;
    this.form.controls.name.setValue(firstName);
    this.form.controls.description.setValue(description);
  }

  onSelectCategory(category: Category) {
    const { name } = category;
    this.selectedCategory = category;
    this.form.controls.categoryName.setValue(name);
  }

}

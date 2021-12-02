import {Component, Input, OnInit} from '@angular/core';
import {FormBuilder, Validators} from '@angular/forms';
import {debounceTime, distinctUntilChanged, Observable, switchMap} from 'rxjs';
import {Artist} from 'src/app/dtos/artist';
import {Category} from 'src/app/dtos/category';
import {ArtistService} from 'src/app/services/artist.service';
import {CategoryService} from 'src/app/services/category.service';

@Component({
  selector: 'app-create-artist',
  templateUrl: './create-artist.component.html',
  styleUrls: ['./create-artist.component.scss']
})
export class CreateArtistComponent implements OnInit {

  @Input() handleNext: (values: any) => void;
  @Input() setErrorFlag: (message?: string) => void;

  artists: Observable<Artist[]>;
  categories: Observable<Category[]>;
  selectedArtist: Artist;
  selectedCategory: Category;
  isNewArtist = false;
  isNewCategory = false;
  artistSubmitted = false;
  categorySubmitted = false;

  form = this.formBuilder.group({
    name: [null, Validators.required],
    description: [{value: null, disabled: true}],
    categoryName: [null, Validators.required]
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
    if (this.isNewArtist) {
      this.selectedArtist = null;
      this.form.controls.name.setValue(null);
      this.form.controls.description.enable();
    } else {
      this.form.controls.description.disable();
    }
  }

  handleNewCategory() {
    this.isNewCategory = !this.isNewCategory;
    if (this.isNewCategory) {
      this.selectedCategory = null;
      this.form.controls.categoryName.setValue(null);
    }
  }

  async nextStep() {
    if (!this.form.valid) {
      return;
    }
    if (this.isNewArtist) {
      await this.submitArtistChanges();
    } else {
      this.artistSubmitted = true;
    }
    if (this.isNewCategory) {
      await this.submitCategoryChanges();
    } else {
      this.categorySubmitted = true;
    }
    if (!this.isNewArtist && !this.isNewCategory) {
      if (!this.selectedArtist || !this.selectedCategory) {
        this.setErrorFlag('Please Select Artist and Category from Dropdown or create new ones.');
      } else {
        this.handleNext({selectedArtist: this.selectedArtist, selectedCategory: this.selectedCategory});
      }
    }
  }

  onSelectArtist(artist: Artist) {
    const {bandName, description} = artist;
    this.selectedArtist = artist;
    this.form.controls.name.setValue(bandName);
    this.form.controls.description.setValue(description);
  }

  onSelectCategory(category: Category) {
    const {name} = category;
    this.selectedCategory = category;
    this.form.controls.categoryName.setValue(name);
  }

  async submitArtistChanges() {
    const artist = new Artist();
    artist.bandName = this.form.value.name;
    artist.description = this.form.value.description;
    this.artistService.createArtist(artist).subscribe({
      next: next => {
        this.selectedArtist = next;
      },
      error: error => {
        this.setErrorFlag();
      },
      complete: () => {
        this.artistSubmitted = true;
        if (this.categorySubmitted) {
          this.handleNext({selectedArtist: this.selectedArtist, selectedCategory: this.selectedCategory});
        }
      }
    });
  }

  async submitCategoryChanges() {
    const category = new Category();
    category.name = this.form.value.categoryName;
    this.categoryService.createCategory(category).subscribe({
      next: next => {
        this.selectedCategory = next;
      },
      error: error => {
        this.setErrorFlag();
      },
      complete: () => {
        this.categorySubmitted = true;
        if (this.artistSubmitted) {
          this.handleNext({selectedArtist: this.selectedArtist, selectedCategory: this.selectedCategory});
        }
      }
    });
  }

}
